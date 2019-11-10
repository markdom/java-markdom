package io.markdom.handler.commonmark;

import java.util.Optional;
import java.util.Stack;
import java.util.StringTokenizer;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.handler.MarkdomHandler;
import io.markdom.util.ObjectHelper;
import lombok.Data;
import lombok.SneakyThrows;

public final class CommonmarkTextMarkdomHandler<ActualAppendable extends Appendable> implements MarkdomHandler<ActualAppendable> {

	private static interface ListIndex {

		public String nextIndicator();

	}

	@Data
	private final static class UnorderedListIndex implements ListIndex {

		@Override
		public String nextIndicator() {
			return "* ";
		}

	}

	@Data
	private final static class OrderedListIndex implements ListIndex {

		private int nextIndex;

		public OrderedListIndex(int startIndex) {
			this.nextIndex = startIndex;
		}

		@Override
		public String nextIndicator() {
			return (nextIndex++) + ". ";
		}

	}

	@Data
	private final static class Delimiter {

		private final String literal;

		private boolean empty = true;

		public Delimiter(String literal) {
			this.literal = literal;
		}

	}

	private final Stack<String> lineStarts = new Stack<String>();

	private final Stack<ListIndex> listIndices = new Stack<ListIndex>();

	private final Stack<Delimiter> delimiters = new Stack<Delimiter>();

	private final ActualAppendable appendable;

	private MarkdomBlockType lastEndedBlock;

	private boolean emptyBlocks;

	private boolean paragraphLine;

	private boolean lineStarted;

	private boolean onlyDigitsInLine;

	private boolean pendingSpace;

	public CommonmarkTextMarkdomHandler(ActualAppendable appendable) throws IllegalArgumentException {
		this.appendable = ObjectHelper.notNull("appendable", appendable);
	}

	@Override
	public void onDocumentBegin() {
	}

	@Override
	public final void onBlocksBegin() {
		emptyBlocks = true;
	}

	@Override
	public final void onBlockBegin(MarkdomBlockType type) {
		emptyBlocks = false;
	}

	@Override
	public final void onCodeBlock(String code, Optional<String> hint) {
		String backtickString = repeat("`", Math.max(3, longestBacktickSequence(code) + 1));
		startLine();
		append(backtickString);
		if (hint.isPresent()) {
			append(hint.get());
		}
		append("\n");
		startLine();
		StringTokenizer tokenizer = new StringTokenizer(code, "\n", true);
		boolean endedWithNewline = false;
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			append(token);
			if ("\n".equals(token)) {
				endedWithNewline = true;
				startLine();
			} else {
				endedWithNewline = false;
			}
		}
		if (!endedWithNewline) {
			append("\n");
			startLine();
		}
		append(backtickString);
		append("\n");
	}

	@Override
	public final void onCommentBlock(String comment) {
		startLine();
		append("<!--");
		boolean firstLine = true;
		StringTokenizer tokenizer = new StringTokenizer(comment, "\n", true);
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (firstLine) {
				firstLine = false;
			}
			append(token);
			if ("\n".equals(token)) {
				startLine();
			}
		}
		append("-->");
		append("\n");
	}

	@Override
	public final void onDivisionBlock() {
		startLine();
		append("---");
		append("\n");
	}

	@Override
	public final void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		startLine();
		append(repeat("#", level.ordinal() + 1));
		append(" ");
		beginContent(false);
	}

	@Override
	public final void onHeadingBlockEnd(MarkdomHeadingLevel level) {
		append("\n");
		delimiters.pop();
	}

	@Override
	public final void onOrderedListBlockBegin(Integer startIndex) {
		displaceAdjacentLists();
		listIndices.push(new OrderedListIndex(startIndex));
	}

	@Override
	public final void onOrderedListBlockEnd(Integer startIndex) {
		listIndices.pop();
	}

	@Override
	public final void onParagraphBlockBegin() {
		startLine();
		beginContent(true);
	}

	@Override
	public final void onParagraphBlockEnd() {
		delimiters.pop();
		append("\n");
	}

	@Override
	public final void onQuoteBlockBegin() {
		lineStarts.push("> ");
	}

	@Override
	public final void onQuoteBlockEnd() {
		lineStarts.pop();
	}

	@Override
	public final void onUnorderedListBlockBegin() {
		displaceAdjacentLists();
		listIndices.push(new UnorderedListIndex());
	}

	@Override
	public final void onUnorderedListBlockEnd() {
		listIndices.pop();
	}

	@Override
	public final void onBlockEnd(MarkdomBlockType type) {
		lastEndedBlock = type;
	}

	@Override
	public final void onNextBlock() {
		startLine();
		append("\n");
	}

	@Override
	public final void onBlocksEnd() {
		if (emptyBlocks) {
			startLine();
			append("\n");
		}
		emptyBlocks = false;
	}

	@Override
	public void onListItemsBegin() {
	}

	@Override
	public final void onListItemBegin() {
		String indicator = listIndices.peek().nextIndicator();
		lineStarts.push(indicator);
		startLine();
		append("\n");
		lineStarts.pop();
		lineStarts.push(repeat(" ", indicator.length()));
		lastEndedBlock = null;
	}

	@Override
	public final void onListItemEnd() {
		lineStarts.pop();
	}

	@Override
	public void onNextListItem() {
	}

	@Override
	public void onListItemsEnd() {
	}

	@Override
	public void onContentsBegin() {
	}

	@Override
	public void onContentBegin(MarkdomContentType type) {
	}

	@Override
	public final void onCodeContent(String code) {
		StringBuilder builder = new StringBuilder((int) (code.length() * 1.2));
		appendPendingDelimiters(builder);
		int backticks = longestBacktickSequence(code);
		String backtickString = repeat("`", backticks + 1);
		builder.append(backtickString);
		if (0 == code.length()) {
			builder.append(" ");
		} else {
			if ('`' == code.charAt(0)) {
				builder.append(" ");
			}
			builder.append(code);
			if ('`' == code.charAt(code.length() - 1)) {
				builder.append(" ");
			}
		}
		builder.append(backtickString);
		append(builder.toString());
		onlyDigitsInLine = false;
		lineStarted = true;
	}

	@Override
	public final void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		delimiters.push(new Delimiter(emphasisLevelDelimiter(level)));
	}

	@Override
	public final void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
		Delimiter delimiter = delimiters.pop();
		if (!delimiter.empty) {
			append(delimiter.literal);
		}
	}

	@Override
	public final void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		StringBuilder builder = new StringBuilder();
		builder.append("![");
		if (alternative.isPresent()) {
			builder.append(alternative.get());
		}
		builder.append("](");
		builder.append(uri);
		if (title.isPresent()) {
			builder.append(" \"");
			builder.append(title.get());
			builder.append("\"");
		}
		builder.append(")");
		append(builder.toString());
		onlyDigitsInLine = false;
		lineStarted = true;
	}

	@Override
	public final void onLineBreakContent(Boolean hard) {
		if (paragraphLine) {
			if (hard) {
				append("\\");
			}
			append("\n");
			startLine();
			beginLine();
		} else {
			pendingSpace = lineStarted;
		}
	}

	@Override
	public final void onLinkContentBegin(String uri, Optional<String> title) {
		delimiters.push(new Delimiter("["));
	}

	@Override
	public final void onLinkContentEnd(String uri, Optional<String> title) {
		// TODO: handle escaping, title, empty params
		if (delimiters.peek().isEmpty()) {
			onTextContent(uri);
		}
		delimiters.pop();
		append("](" + uri + ")");
	}

	@Override
	public final void onTextContent(String text) {
		StringBuilder builder = new StringBuilder((int) (text.length() * 1.2));
		for (int i = 0, n = text.length(); i < n; i++) {
			char c = text.charAt(i);
			if (Character.CONTROL != Character.getType(c)) {
				switch (c) {
					case '\\':
					case '`':
					case '*':
					case '_':
					case '[':
					case ']':
						appendPendingDelimiters(builder);
						builder.append('\\');
						builder.append(c);
						break;
					case '#':
					case '+':
					case '-':
						appendPendingDelimiters(builder);
						// escape, if c is the first character of a line and the
						// following character, if present, is a whitespace
						if (paragraphLine && !lineStarted) {
							char next = (i + 1 < n) ? text.charAt(i + 1) : 0;
							if (' ' == next || '\t' == next) {
								builder.append('\\');
							}
						}
						builder.append(c);
						break;
					case '.':
						appendPendingDelimiters(builder);
						// escape, c if there are characters in the line and all of
						// them are digits
						if (paragraphLine && lineStarted && onlyDigitsInLine) {
							builder.append('\\');
						}
						builder.append(c);
						break;
					case ' ':
						pendingSpace = lineStarted;
						break;
					default:
						appendPendingDelimiters(builder);
						onlyDigitsInLine &= isDigit(c);
						builder.append(c);
				}
			} else if ('\t' == c || '\n' == c) {
				// treat tab as space
				pendingSpace = lineStarted;
			}
		}
		append(builder.toString());
	}

	@Override
	public void onContentEnd(MarkdomContentType type) {
	}

	@Override
	public void onNextContent() {
	}

	@Override
	public void onContentsEnd() {
	}

	@Override
	public void onDocumentEnd() {
	}

	@Override
	public ActualAppendable getResult() {
		return appendable;
	}

	private String emphasisLevelDelimiter(MarkdomEmphasisLevel level) {
		switch (level) {
			case LEVEL_1:
				return "*";
			case LEVEL_2:
				return "**";
		}
		throw new InternalError("Unexpected emphasis level: " + level);
	}

	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}

	private void appendPendingDelimiters(StringBuilder builder) {
		if (pendingSpace) {
			builder.append(" ");
		}
		pendingSpace = false;
		lineStarted = true;
		for (int i = delimiters.size() - 1; i >= 0; i--) {
			Delimiter delimiter = delimiters.get(i);
			if (delimiter.empty) {
				builder.append(delimiter.literal);
				delimiter.empty = false;
				onlyDigitsInLine = false;
			}
		}
	}

	private final void displaceAdjacentLists() {
		if (MarkdomBlockType.UNORDERED_LIST == lastEndedBlock || MarkdomBlockType.ORDERED_LIST == lastEndedBlock) {
			onBlockBegin(MarkdomBlockType.DIVISION);
			onCommentBlock("");
			onBlockEnd(MarkdomBlockType.DIVISION);
		}
	}

	private String repeat(String string, int amount) {
		StringBuilder builder = new StringBuilder(string.length() * amount);
		for (int i = 0; i < amount; i++) {
			builder.append(string);
		}
		return builder.toString();
	}

	private void beginContent(boolean paragraphLine) {
		Delimiter delimiter = new Delimiter("");
		delimiter.empty = false;
		delimiters.push(delimiter);
		this.paragraphLine = paragraphLine;
		beginLine();
	}

	private void beginLine() {
		lineStarted = false;
		pendingSpace = false;
		onlyDigitsInLine = true;
	}

	private void startLine() {
		for (int i = 0, n = lineStarts.size(); i < n; i++) {
			String linePrefix = lineStarts.get(i);
			append(linePrefix);
		}
	}

	@SneakyThrows
	private void append(String text) {
		if (null != text) {
			appendable.append(text);
		}
	}

	private int longestBacktickSequence(String code) {
		int maxLength = 0;
		int currentLength = 0;
		for (int i = 0, n = code.length(); i < n; i++) {
			if ('`' == code.charAt(i)) {
				currentLength++;
			} else {
				maxLength = Math.max(maxLength, currentLength);
				currentLength = 0;
			}
		}
		return Math.max(maxLength, currentLength);
	}

}
