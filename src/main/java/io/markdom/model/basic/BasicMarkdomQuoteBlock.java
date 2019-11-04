package io.markdom.model.basic;

import java.util.List;
import java.util.Objects;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomBlock;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomQuoteBlock;

public final class BasicMarkdomQuoteBlock extends AbstractMarkdomBlock implements MarkdomQuoteBlock {

	private final BasicMarkdomBlockParentDelegate delegate = new BasicMarkdomBlockParentDelegate(this);

	public BasicMarkdomQuoteBlock(MarkdomFactory factory) {
		super(factory);
	}

	@Override
	public List<? extends MarkdomBlock> getBlocks() {
		return delegate.getBlocks();
	}

	@Override
	public final BasicMarkdomQuoteBlock addBlock(MarkdomBlock block) {
		delegate.addBlock(block);
		return this;
	}

	@Override
	public final BasicMarkdomQuoteBlock addBlocks(MarkdomBlock... blocks) {
		delegate.addBlocks(blocks);
		return this;
	}

	@Override
	public final BasicMarkdomQuoteBlock addBlocks(Iterable<MarkdomBlock> blocks) {
		delegate.addBlocks(blocks);
		return this;
	}

	@Override
	public void doHandle(MarkdomHandler<?> handler) {
		handler.onQuoteBlockBegin();
		delegate.onHandle(handler);
		handler.onQuoteBlockEnd();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getBlocks());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (null == object) {
			return false;
		} else if (!(object instanceof MarkdomQuoteBlock)) {
			return false;
		}
		MarkdomQuoteBlock other = (MarkdomQuoteBlock) object;
		if (!getBlocks().equals(other.getBlocks())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [blocks=" + getBlocks() + "]";
	}

}
