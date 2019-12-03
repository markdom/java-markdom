package io.markdom.handler.text.commonmark;

public class HeadingAppendable implements LineAppendable {

	private final LineAppendable appendable;

	private final String lineDivisionString;

	private boolean pendingLineEnd;

	HeadingAppendable(LineAppendable appendable, String lineDivisionString) {
		this.appendable = appendable;
		this.lineDivisionString = lineDivisionString;
	}

	@Override
	public void startLine() {
		if (pendingLineEnd) {
			pendingLineEnd = false;
			appendable.append(lineDivisionString);
		}
	}

	@Override
	public void append(char character) {
		appendable.append(character);
	}

	@Override
	public void append(CharSequence characters) {
		appendable.append(characters);
	}

	@Override
	public void endLine() {
		pendingLineEnd = true;
	}

}
