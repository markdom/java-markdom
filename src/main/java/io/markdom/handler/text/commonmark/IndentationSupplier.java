package io.markdom.handler.text.commonmark;

import java.util.function.Supplier;

public interface IndentationSupplier extends Supplier<String> {

	@Override
	public String get();

}
