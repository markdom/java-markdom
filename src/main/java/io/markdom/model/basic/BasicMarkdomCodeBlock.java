package io.markdom.model.basic;

import java.util.Optional;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomCodeBlock;
import io.markdom.model.MarkdomFactory;

public final class BasicMarkdomCodeBlock extends AbstractMarkdomBlock implements MarkdomCodeBlock {

	private String code;

	private Optional<String> hint;

	BasicMarkdomCodeBlock(MarkdomFactory factory, String code, Optional<String> hint) {
		super(factory);
		setCode(code);
		setHint(hint);
	}

	@Override
	public String getCode() {
		return code;
	}

	public BasicMarkdomCodeBlock setCode(String code) {
		if (null == code) {
			throw new IllegalArgumentException("The given code is null");
		}
		this.code = code;
		return this;
	}

	@Override
	public Optional<String> getHint() {
		return hint;
	}

	public BasicMarkdomCodeBlock setHint(Optional<String> hint) {
		if (null == hint) {
			throw new IllegalArgumentException("The given hint is null");
		}
		this.hint = hint;
		return this;
	}

	@Override
	public void doHandle(MarkdomHandler<?> handler) {
		handler.onCodeBlock(code, hint);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [code=" + code + "hint=" + hint + "]";
	}

}
