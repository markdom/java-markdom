package io.markdom.model.basic;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomTextContent;

public final class BasicMarkdomTextContent extends AbstractMarkdomContent implements MarkdomTextContent {

	private String text;

	public BasicMarkdomTextContent(MarkdomFactory factory, String text) {
		super(factory);
		setText(text);
	}

	@Override
	public String getText() {
		return text;
	}

	public BasicMarkdomTextContent setText(String text) {
		if (null == text) {
			throw new IllegalArgumentException("The given text is null");
		}
		this.text = text;
		return this;
	}

	@Override
	public void doHandle(MarkdomHandler<?> handler) {
		handler.onTextContent(text);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [text=" + text + "]";
	}

}
