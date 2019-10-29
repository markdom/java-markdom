package io.markdom.model.basic;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomDivisionBlock;
import io.markdom.model.MarkdomFactory;

public final class BasicMarkdomDivisionBlock extends AbstractMarkdomBlock implements MarkdomDivisionBlock {

	BasicMarkdomDivisionBlock(MarkdomFactory factory) {
		super(factory);
	}

	@Override
	public void doHandle(MarkdomHandler<?> handler) {
		handler.onDivisionBlock();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " []";
	}

}
