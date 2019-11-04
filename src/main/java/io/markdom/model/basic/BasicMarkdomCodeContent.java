package io.markdom.model.basic;

import java.util.Objects;

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
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (null == object) {
			return false;
		} else if (!(object instanceof MarkdomCodeContent)) {
			return false;
		}
		MarkdomCodeContent other = (MarkdomCodeContent) object;
		if (!Objects.equals(code, other.getCode())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [code=" + code + "]";
	}

}
