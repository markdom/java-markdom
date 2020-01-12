package io.markdom.handler.text.commonmark;

public final class EmptySection implements Section {

	@Override
	public void appendTo(LineAppendable appendable) {
		appendable.startLine();
		appendable.endLine();
	}

}
