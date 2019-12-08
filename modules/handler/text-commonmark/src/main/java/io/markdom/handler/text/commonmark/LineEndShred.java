package io.markdom.handler.text.commonmark;

import lombok.Getter;

final class LineEndShred implements Shred {

	private String lineBreakString;

	@Getter
	private boolean hard;

	LineEndShred(boolean hard, String lineBreakString) {
		this.lineBreakString = lineBreakString;
		appendLineBreak(hard);
	}

	@Override
	public ShredType getType() {
		return ShredType.LINE_END;
	}

	@Override
	public boolean isLineEnding() {
		return true;
	}

	@Override
	public boolean appendLineBreak(boolean hard) {
		if (hard) {
			this.hard = hard;
		}
		return true;
	}

	@Override
	public boolean consumesSpace(Position position) {
		return Position.LEADING == position;
	}

	@Override
	public void appendTo(LineAppendable appendable) {
		if (hard) {
			appendable.append(lineBreakString);
		}
		appendable.endLine();
	}

	@Override
	public String toString() {
		return "(" + hard + ")";
	}

}