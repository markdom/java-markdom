package io.markdom.handler.text.commonmark;

import io.markdom.util.StringUtil;

final class TextShred implements Shred {

	private final StringBuilder buffer = new StringBuilder();

	private boolean leadingSpace;

	private boolean trailingSpace;

	private boolean isLeadingTextShredInLine;

	private boolean isTrailingTextShredInLine;

	private boolean isFollowedByLinkBeginShred;

	TextShred(String text) {
		appendText(text);
	}

	@Override
	public ShredType getType() {
		return ShredType.TEXT;
	}

	@Override
	public boolean appendText(String text) {
		for (char character : text.toCharArray()) {
			switch (character) {
				case '\t':
				case ' ':
					if (isEmpty()) {
						leadingSpace = true;
					} else {
						trailingSpace = true;
					}
					break;
				default:
					if (trailingSpace) {
						buffer.append(' ');
						trailingSpace = false;
					}
					buffer.append(character);
					break;
			}
		}
		return true;
	}

	@Override
	public boolean consumesSpace(Position position) {
		return true;
	}

	@Override
	public boolean hasSpace(Position position) {
		switch (position) {
			case LEADING:
				return leadingSpace;
			case TRAILING:
				return trailingSpace;
		}
		throw new InternalError("Unknown space position: " + position);
	}

	@Override
	public void setSpace(Position position, boolean space) {
		switch (position) {
			case LEADING:
				leadingSpace = space;
				break;
			case TRAILING:
				trailingSpace = space;
				break;
		}
	}

	@Override
	public void setHint(Position position, ShredType type) {
		if (Position.LEADING == position && ShredType.LINE_START == type) {
			isLeadingTextShredInLine = true;
		}
		if (Position.TRAILING == position && ShredType.LINE_END == type) {
			isTrailingTextShredInLine = true;
		}
		if (Position.TRAILING == position && ShredType.LINK_BEGIN == type) {
			isFollowedByLinkBeginShred = true;
		}
	}

	@Override
	public boolean isEmpty() {
		return 0 == buffer.length();
	}

	@Override
	public void appendTo(LineAppendable appendable) {
		if (leadingSpace) {
			appendable.append(' ');
		}
		if (isLeadingTextShredInLine && isTrailingTextShredInLine) {
			processAsOnlyShredInLine(appendable);
		} else if (isLeadingTextShredInLine) {
			processAsLeadingShredInLine(appendable, false);
		} else {
			appendAndEscapeBuffer(appendable, 0, false);
		}
		if (trailingSpace) {
			appendable.append(' ');
		}
	}

	private void processAsOnlyShredInLine(LineAppendable appendable) {
		if (isHeadingIndicator() || isFencedCodeIndicator() || isDivisionIndicator() || isUnorderedListItemIndicator()) {
			appendable.append('\\');
			appendable.append(buffer);
		} else {
			processAsLeadingShredInLine(appendable, true);
		}
	}

	private boolean isHeadingIndicator() {
		if (buffer.length() <= 6 && StringUtil.consistsOnlyOf(buffer, '#')) {
			return true;
		}
		if (StringUtil.consistsOnlyOf(buffer, '=')) {
			return true;
		}
		return false;
	}

	private boolean isFencedCodeIndicator() {
		if (buffer.length() >= 3 && StringUtil.leadingSequenceLength(buffer, '~') >= 3) {
			return true;
		}
		return false;
	}

	private boolean isDivisionIndicator() {
		if (buffer.length() >= 3) {
			if (StringUtil.consistsOnlyOfAnyOf(buffer, new char[] { '-', ' ' })) {
				return true;
			}
			if (StringUtil.consistsOnlyOfAnyOf(buffer, new char[] { '*', ' ' })) {
				return true;
			}
			if (StringUtil.consistsOnlyOfAnyOf(buffer, new char[] { '_', ' ' })) {
				return true;
			}
		}
		return false;
	}

	private boolean isUnorderedListItemIndicator() {
		if (buffer.length() == 1) {
			char character = buffer.charAt(0);
			if ('-' == character || '+' == character || '*' == character) {
				return true;
			}
		}
		return false;
	}

	private void processAsLeadingShredInLine(LineAppendable appendable, boolean isOnlyShredInLine) {
		if (startsWithHeadingIndicator() || startsWithFencedCodeIndicator() || startsWithUnorderedListItemIndicator()) {
			appendable.append('\\');
			appendable.append(buffer.charAt(0));
			appendAndEscapeBuffer(appendable, 1, isOnlyShredInLine);
		} else {
			appendAndEscapeBuffer(appendable, 0, isOnlyShredInLine);
		}
	}

	private boolean startsWithHeadingIndicator() {
		int leadingHashSequenceLength = StringUtil.leadingSequenceLength(buffer, '#');
		return leadingHashSequenceLength > 0 && leadingHashSequenceLength <= 6 && isFollowedBySpace(leadingHashSequenceLength - 1);
	}

	private boolean startsWithFencedCodeIndicator() {
		return StringUtil.leadingSequenceLength(buffer, '~') >= 3;
	}

	private boolean startsWithUnorderedListItemIndicator() {
		if (buffer.length() > 0) {
			char character = buffer.charAt(0);
			if ('-' == character || '+' == character || '*' == character) {
				return isFollowedBySpace(0);
			}
		}
		return false;
	}

	private boolean isFollowedBySpace(int i) {
		return (buffer.length() > i + 1 && buffer.charAt(i + 1) == ' ') || (buffer.length() == i + 1 && trailingSpace);
	}

	private void appendAndEscapeBuffer(LineAppendable appendable, int fromIndex, boolean isOnlyShredInLine) {
		boolean onlyNumbersSoFar = false;
		for (int i = fromIndex, n = buffer.length(); i < n; i++) {
			char character = buffer.charAt(i);
			boolean isFirstCharacter = i == 0;
			boolean isLastCharacter = i == n - 1;
			boolean couldBeOrderedList = onlyNumbersSoFar && (isOnlyShredInLine || isFollowedBySpace(i));
			appendAndEscapeCharacter(appendable, character, couldBeOrderedList, isLastCharacter);
			onlyNumbersSoFar = onlyNumbersSoFar(character, onlyNumbersSoFar, isFirstCharacter);
		}
	}

	private void appendAndEscapeCharacter(LineAppendable appendable, char character, boolean couldBeOrderedList, boolean isLastCharacter) {
		switch (character) {
			case '\\':
			case '`':
			case '*':
			case '_':
			case '[':
			case '<':
				appendable.append('\\');
				appendable.append(character);
				break;
			case '!':
				if (isFollowedByLinkBeginShred && isLastCharacter) {
					appendable.append('\\');
				}
				appendable.append(character);
				break;
			case '.':
			case ')':
				if (couldBeOrderedList) {
					appendable.append('\\');
				}
				appendable.append(character);
				break;
			default:
				appendable.append(character);
				break;
		}
	}

	private boolean onlyNumbersSoFar(char character, boolean onlyNumbersSoFar, boolean isFirstCharacter) {
		if (isFirstCharacter) {
			onlyNumbersSoFar = isNumber(character);
		} else if (onlyNumbersSoFar) {
			if (!isNumber(character)) {
				onlyNumbersSoFar = false;
			}
		}
		return onlyNumbersSoFar;
	}

	private boolean isNumber(char character) {
		return character >= '0' && character <= '9';
	}

	@Override
	public String toString() {
		return "(" + leadingSpace + ", " + buffer + ", " + trailingSpace + ")";
	}

}