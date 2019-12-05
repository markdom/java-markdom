package io.markdom.model.basic;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomBlockParent;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.AbstractManagedList.AfterInsertAction;
import io.markdom.model.basic.AbstractManagedList.AfterRemoveAction;
import io.markdom.util.ObjectHelper;
import lombok.AllArgsConstructor;

abstract class AbstractMarkdomBlock extends AbstractMarkdomNode implements ManagedMarkdomBlock {

	@AllArgsConstructor
	private class BlockAction implements AfterInsertAction<ManagedMarkdomBlock>, AfterRemoveAction<ManagedMarkdomBlock> {

		private final MarkdomBlockParent parent;

		@Override
		public ManagedMarkdomBlock getManagedPayload() {
			return AbstractMarkdomBlock.this;
		}

		@Override
		public void perform() {
			AbstractMarkdomBlock.this.parent = parent;
		}

	}

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
	public final BlockAction onAttach(MarkdomBlockParent parent) {
		ObjectHelper.notNull("block parent", parent);
		if (null != this.parent) {
			throw new IllegalStateException("This block is already attached to a block parent");
		}
		return new BlockAction(parent);
	}

	@Override
	public final BlockAction onDetach(MarkdomBlockParent parent) {
		ObjectHelper.notNull("block parent", parent);
		if (null == this.parent) {
			throw new IllegalStateException("This block is currently not attached to a block parent");
		}
		if (this.parent != parent) {
			throw new IllegalStateException("This block is not attached to the given block parent");
		}
		return new BlockAction(null);
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
