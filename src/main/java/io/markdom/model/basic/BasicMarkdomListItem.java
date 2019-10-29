package io.markdom.model.basic;

import java.util.List;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomBlock;
import io.markdom.model.MarkdomFactory;

public final class BasicMarkdomListItem extends AbstractMarkdomListItem {

	private final BasicMarkdomBlockParentDelegate delegate = new BasicMarkdomBlockParentDelegate(this);

	BasicMarkdomListItem(MarkdomFactory factory) {
		super(factory);
	}

	@Override
	public final List<? extends MarkdomBlock> getBlocks() {
		return delegate.getBlocks();
	}

	@Override
	public final BasicMarkdomListItem addBlock(MarkdomBlock block) {
		delegate.addBlock(block);
		return this;
	}

	@Override
	public final BasicMarkdomListItem addBlocks(MarkdomBlock... blocks) {
		delegate.addBlocks(blocks);
		return this;
	}

	@Override
	public final BasicMarkdomListItem addBlocks(Iterable<MarkdomBlock> blocks) {
		delegate.addBlocks(blocks);
		return this;
	}

	@Override
	protected void doHandle(MarkdomHandler<?> handler) {
		delegate.onHandle(handler);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [blocks=" + getBlocks() + "]";
	}

}
