package io.markdom.handler.text.commonmark;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.util.StringUtil;
import lombok.SneakyThrows;

final class ContentBuffer {

	private final CommonmarkTextOptions options;

	private final List<Shred> shreds = new LinkedList<>();

	private final Map<MarkdomEmphasisLevel, String> bounderyStrings;

	private final Set<MarkdomEmphasisLevel> openLevels;

	private final Stack<MarkdomEmphasisLevel> ignoreLevels;

	private final String emptyParagraphString;

	private final String lineBreakString;

	private boolean pendingLineBreak;

	private int linkDepth;

	private boolean skippedEmptyLink;

	public ContentBuffer(CommonmarkTextOptions options, String emptyParagraphString) {
		this.options = options;
		this.bounderyStrings = new EnumMap<>(MarkdomEmphasisLevel.class);
		this.openLevels = EnumSet.noneOf(MarkdomEmphasisLevel.class);
		this.ignoreLevels = new Stack<>();
		for (MarkdomEmphasisLevel level : MarkdomEmphasisLevel.values()) {
			bounderyStrings.put(level, getBounderyStringByLevel(level));
		}
		this.emptyParagraphString = emptyParagraphString;
		this.lineBreakString = options.getLineBreakOption().getLineBreakString();
		beginNewLine();
	}

	private String getBounderyStringByLevel(MarkdomEmphasisLevel level) {
		return StringUtil.repeat(getBounderyCharacterByLevel(level), level.ordinal() + 1);
	}

	private char getBounderyCharacterByLevel(MarkdomEmphasisLevel level) {
		switch (level) {
			case LEVEL_1:
				return options.getEmphasisLevel1Option().getBounderyCharacter();
			case LEVEL_2:
				return options.getEmphasisLevel2Option().getBounderyCharacter();
		}
		throw new InternalError("Unknown emphasis level: " + level);
	}

	public void appendCode(String code) {
		prepareLine();
		if (!lastShred().appendCode(code)) {
			CodeShred codeShred = new CodeShred(code);
			if (!codeShred.isEmpty()) {
				shreds.add(codeShred);
			}
		}
	}

	public void appendEmphasisBegin(MarkdomEmphasisLevel level) {
		if (openLevels.contains(level)) {
			ignoreLevels.push(level);
		} else {
			openLevels.add(level);
			prepareLine();
			if (lastShred().isEmphasisEnd(level)) {
				removeLastShred();
			} else {
				shreds.add(new EmphasisBeginShred(level, bounderyStrings.get(level)));
			}
		}
	}

	public void appendEmphasisEnd(MarkdomEmphasisLevel level) {
		if (!ignoreLevels.isEmpty() && level == ignoreLevels.peek()) {
			ignoreLevels.pop();
		} else {
			openLevels.remove(level);
			LineEndShred lineEndShred = null;
			if (lastShred().isLineEnding()) {
				lineEndShred = (LineEndShred) removeLastShred();
			}
			if (lastShred().isEmphasisBegin(level)) {
				removeLastShred();
			} else {
				shreds.add(new EmphasisEndShred(level, bounderyStrings.get(level)));
			}
			if (null != lineEndShred) {
				shreds.add(lineEndShred);
			}
			if (lastShred().isLineBeginning()) {
				unprepareLine();
			}
		}
	}

	public void appendImage(String uri, Optional<String> title, Optional<String> alternative) {
		prepareLine();
		ImageShred imageShred = new ImageShred(uri, title, alternative);
		if (!imageShred.isEmpty()) {
			shreds.add(imageShred);
		}
	}

	public void appendLinkBegin(String uri) {
		if (0 == linkDepth) {
			if (uri.trim().isEmpty()) {
				skippedEmptyLink = true;
			} else {
				prepareLine();
				shreds.add(new LinkBeginShred());
			}
		}
		linkDepth++;
	}

	public void appendLinkEnd(String uri, Optional<String> title) {
		linkDepth--;
		if (0 == linkDepth) {
			if (skippedEmptyLink) {
				skippedEmptyLink = false;
			} else {
				LineEndShred lineEndShred = null;
				if (lastShred().isLineEnding()) {
					lineEndShred = (LineEndShred) removeLastShred();
				}
				shreds.add(new LinkEndShred(uri, title));
				if (null != lineEndShred) {
					shreds.add(lineEndShred);
				}
				if (lastShred().isLineBeginning()) {
					unprepareLine();
				}
			}
		}
	}

	public void appendText(String text) {
		prepareLine();
		if (!lastShred().appendText(text)) {
			TextShred textShred = new TextShred(text);
			if (!textShred.isEmpty()) {
				shreds.add(textShred);
			}
		}
	}

	public void appendLineBreak(boolean hard) {
		if (!lastShred().appendLineBreak(hard)) {
			shreds.add(new LineEndShred(hard, lineBreakString));
			pendingLineBreak = true;
		}
	}

	private void prepareLine() {
		if (pendingLineBreak) {
			pendingLineBreak = false;
			beginNewLine();
			moveLineBreakInFrontOfBeginnings();
		}
	}

	private void beginNewLine() {
		shreds.add(new LineStartShred());
	}

	private void moveLineBreakInFrontOfBeginnings() {
		LineStartShred lineBeginShred = (LineStartShred) removeLastShred();
		LineEndShred lineEndShred = (LineEndShred) removeLastShred();
		int beginningIndex = shreds.size();
		while (beginningIndex > 1 && shreds.get(beginningIndex - 1).isDelimiterBeginning()) {
			beginningIndex--;
		}
		if (!shreds.get(beginningIndex - 1).isLineBeginning()) {
			shreds.add(beginningIndex, lineBeginShred);
			shreds.add(beginningIndex, lineEndShred);
		} else {
			if (beginningIndex > 1) {
				LineEndShred previousLineEndShred = (LineEndShred) shreds.get(beginningIndex - 2);
				previousLineEndShred.appendLineBreak(lineEndShred.isHard());
			}
		}
	}

	private void unprepareLine() {
		if (!pendingLineBreak) {
			pendingLineBreak = true;
			unbeginNewLine();
		}
	}

	private void unbeginNewLine() {
		if (!isEmpty()) {
			removeLastShred();
		}
	}

	private Shred lastShred() {
		return shreds.get(shreds.size() - 1);
	}

	private Shred removeLastShred() {
		return shreds.remove(shreds.size() - 1);
	}

	private boolean isEmpty() {
		return 1 == shreds.size();
	}

	@SneakyThrows
	public void appendTo(LineAppendable sink) {
		if (isEmpty()) {
			sink.append(emptyParagraphString);
		} else {
			ensureLastShredIsASoftLineBreak();
			shiftLeadingSpaceToTheLeftAndSetLeadingHints();
			shiftTrailingSpaceToTheRightAndSetTrailingHints();
			for (Shred shred : shreds) {
				shred.appendTo(sink);
			}
		}
	}

	private void ensureLastShredIsASoftLineBreak() {
		if (lastShred().isLineEnding()) {
			removeLastShred();
		}
		appendLineBreak(false);
	}

	private void shiftLeadingSpaceToTheLeftAndSetLeadingHints() {
		ListIterator<Shred> iterator = shreds.listIterator(0);
		Shred previousShred = null;
		Shred trailingSpaceConsumer = null;
		while (iterator.hasNext()) {
			Shred currentShred = iterator.next();
			if (null != previousShred) {
				currentShred.setHint(Position.LEADING, previousShred.getType());
			}
			if (currentShred.hasSpace(Position.LEADING)) {
				if (null != trailingSpaceConsumer) {
					trailingSpaceConsumer.setSpace(Position.TRAILING, true);
					currentShred.setSpace(Position.LEADING, false);
				}
			}
			if (currentShred.consumesSpace(Position.TRAILING)) {
				trailingSpaceConsumer = currentShred;
			}
			if (currentShred.blocksSpace(Position.LEADING)) {
				trailingSpaceConsumer = null;
			}
			previousShred = currentShred;
		}
	}

	private void shiftTrailingSpaceToTheRightAndSetTrailingHints() {
		ListIterator<Shred> iterator = shreds.listIterator(shreds.size());
		Shred previousShred = null;
		Shred leadingSpaceConsumer = null;
		while (iterator.hasPrevious()) {
			Shred currentShred = iterator.previous();
			if (null != previousShred) {
				currentShred.setHint(Position.TRAILING, previousShred.getType());
			}
			if (currentShred.hasSpace(Position.TRAILING)) {
				if (null != leadingSpaceConsumer) {
					leadingSpaceConsumer.setSpace(Position.LEADING, true);
					currentShred.setSpace(Position.TRAILING, false);
				}
			}
			if (currentShred.consumesSpace(Position.LEADING)) {
				leadingSpaceConsumer = currentShred;
			}
			if (currentShred.blocksSpace(Position.TRAILING)) {
				leadingSpaceConsumer = null;
			}
			previousShred = currentShred;
		}
	}

}
