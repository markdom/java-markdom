package io.markdom.handler.text.commonmark;

final class LineStartShred implements Shred {

	LineStartShred() {
	}

	@Override
	public boolean isLineBeginning() {
		return true;
	}

	@Override
	public ShredType getType() {
		return ShredType.LINE_START;
	}

	@Override
	public boolean appendLineBreak(boolean hard) {
		return true;
	}

	@Override
	public boolean consumesSpace(Position position) {
		return Position.TRAILING == position;
	}

	@Override
	public void appendTo(LineAppendable appendable) {
		appendable.startLine();
	}

	@Override
	public String toString() {
		return "()";
	}

}