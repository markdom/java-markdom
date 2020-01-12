package io.markdom.handler.text.commonmark;

import lombok.SneakyThrows;

public class IndentingAppendable implements LineAppendable {

	private final String lineEndString;

	private final IndentationSupplier supplier;

	private final Appendable appendable;

	private boolean pendingLineEnd;

	IndentingAppendable(CommonmarkTextConfiguration configuration, IndentationSupplier indentationSupplier, Appendable appendable) {
		this.lineEndString = configuration.getLineEndOption().getLineEndString();
		this.supplier = indentationSupplier;
		this.appendable = appendable;
	}

	@Override
	@SneakyThrows
	public void startLine() {
		if (pendingLineEnd) {
			appendable.append(lineEndString);
		}
		appendable.append(supplier.get());
	}

	@Override
	@SneakyThrows
	public void append(char character) {
		appendable.append(character);
	}

	@Override
	@SneakyThrows
	public void append(CharSequence characters) {
		appendable.append(characters);
	}

	@Override
	public void endLine() {
		pendingLineEnd = true;
	}

}
