package io.markdom.handler.text.commonmark;

import io.markdom.util.StringUtil;
import lombok.SneakyThrows;

final class CodeShred implements Shred {

	private final StringBuilder buffer = new StringBuilder();

	private boolean pendingLineBreak;

	private boolean trailingSpace;

	CodeShred(String code) {
		appendCode(code);
	}

	@Override
	public ShredType getType() {
		return ShredType.CODE;
	}

	@Override
	public boolean appendCode(String code) {
		for (int i = 0, n = code.length(); i < n; i++) {
			char character = code.charAt(i);
			switch (character) {
				case '\r':
					break;
				case '\n':
					if (!isEmpty()) {
						pendingLineBreak = true;
					}
					break;
				case ' ':
				case '\t':
					if (pendingLineBreak) {
						if (!trailingSpace) {
							buffer.append(' ');
						}
						pendingLineBreak = false;
					} else {
						buffer.append(' ');
					}
					trailingSpace = true;
					break;
				default:
					if (pendingLineBreak) {
						if (!trailingSpace) {
							buffer.append(' ');
						}
						pendingLineBreak = false;
					}
					buffer.append(character);
					trailingSpace = false;
					break;
			}
		}
		return true;
	}

	@Override
	public boolean blocksSpace(Position position) {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return 0 == buffer.length();
	}

	@Override
	@SneakyThrows
	public void appendTo(LineAppendable appendable) {
		int fenceLength = Math.max(1, StringUtil.longestSequenceLength(buffer, '`') + 1);
		String fence = StringUtil.repeat('`', fenceLength);
		appendable.append(fence);
		boolean needsExtraSpace = needsExtraSpace();
		if (needsExtraSpace) {
			appendable.append(" ");
		}
		appendable.append(buffer);
		if (needsExtraSpace) {
			appendable.append(" ");
		}
		appendable.append(fence);
	}

	private boolean needsExtraSpace() {
		char firstCharacter = buffer.charAt(0);
		char lastCharacter = buffer.charAt(buffer.length() - 1);
		boolean onlySpaces = StringUtil.consistsOnlyOf(buffer, ' ');
		return !onlySpaces && (needsExtraSpace(firstCharacter) || needsExtraSpace(lastCharacter));
	}

	private boolean needsExtraSpace(char character) {
		return '`' == character || ' ' == character;
	}

	@Override
	public String toString() {
		return "(`" + buffer + "`)";
	}

}
