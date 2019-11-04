package io.markdom.model.basic;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.ManagedMarkdomBlock;
import io.markdom.model.MarkdomBlockParent;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;

abstract class AbstractMarkdomBlock extends AbstractMarkdomNode implements ManagedMarkdomBlock {

	private MarkdomBlockParent parent;

	AbstractMarkdomBlock(MarkdomFactory factory) {
		super(factory);
	}

	@Override
	public final boolean hasParent() {
		return null != parent;
	}

	@Override
	public final Optional<MarkdomBlockParent> getParent() {
		return Optional.ofNullable(parent);
	}

	@Override
	public final Optional<Integer> getIndex() {
		return getParent().map(parent -> parent.getBlocks().indexOf(this));
	}

	@Override
	public final Optional<MarkdomDocument> getDocument() {
		return getParent().flatMap(MarkdomBlockParent::getDocument);
	}

	@Override
	public final Runnable onAttach(MarkdomBlockParent parent) {
		if (null == parent) {
			throw new IllegalArgumentException("The given Markdom block parent is null");
		}
		if (null != this.parent) {
			throw new IllegalStateException("This Markdom block is already attached to a block parent");
		}
		return () -> AbstractMarkdomBlock.this.parent = parent;
	}

	@Override
	public final Runnable onDetach(MarkdomBlockParent parent) {
		if (null == this.parent) {
			throw new IllegalStateException("This Markdom block is currently not attached to a block parent");
		}
		if (this.parent != parent) {
			throw new IllegalStateException("This Markdom block is not attached to the given block parent");
		}
		return () -> AbstractMarkdomBlock.this.parent = null;
	}

	@Override
	public final void onHandle(MarkdomHandler<?> handler) {
		MarkdomBlockType type = getBlockType();
		handler.onBlockBegin(type);
		doHandle(handler);
		handler.onBlockEnd(type);
	}

	protected abstract void doHandle(MarkdomHandler<?> handler);

}
