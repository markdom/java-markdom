package io.markdom.model.basic;

import java.util.Objects;

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
	public int hashCode() {
		return Objects.hash();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (null == object) {
			return false;
		} else if (!(object instanceof MarkdomDivisionBlock)) {
			return false;
		}
		return false;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " []";
	}

}
