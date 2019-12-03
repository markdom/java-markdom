package io.markdom.handler.text.commonmark;

final class QuoteIndentation implements Indentation {

	@Override
	public void advance() {
	}

	@Override
	public String get() {
		return "> ";
	}

}
