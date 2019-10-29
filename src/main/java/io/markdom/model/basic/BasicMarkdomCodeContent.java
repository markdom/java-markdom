package io.markdom.model.basic;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomCodeContent;
import io.markdom.model.MarkdomFactory;

public final class BasicMarkdomCodeContent extends AbstractMarkdomContent implements MarkdomCodeContent {

	private String code;

	BasicMarkdomCodeContent(MarkdomFactory factory, String code) {
		super(factory);
		setCode(code);
	}

	@Override
	public String getCode() {
		return code;
	}

	public BasicMarkdomCodeContent setCode(String code) {
		if (null == code) {
			throw new IllegalArgumentException("The given code is null");
		}
		this.code = code;
		return this;
	}

	@Override
	public void doHandle(MarkdomHandler<?> handler) {
		handler.onCodeContent(code);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [code=" + code + "]";
	}

}
