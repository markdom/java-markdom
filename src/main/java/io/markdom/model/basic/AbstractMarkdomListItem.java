package io.markdom.model.basic;

import java.util.Optional;

import io.markdom.common.MarkdomBlockParentType;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomListBlock;
import io.markdom.model.basic.AbstractManagedList.AfterInsertAction;
import io.markdom.model.basic.AbstractManagedList.AfterRemoveAction;
import io.markdom.util.ObjectHelper;
import lombok.AllArgsConstructor;

public abstract class AbstractMarkdomListItem extends AbstractMarkdomNode implements ManagedMarkdomListItem {

	@AllArgsConstructor
	private class ManagedListItemAction implements AfterInsertAction<ManagedMarkdomListItem>, AfterRemoveAction<ManagedMarkdomListItem> {

		private final MarkdomListBlock listBlock;

		@Override
		public ManagedMarkdomListItem getManagedPayload() {
			return AbstractMarkdomListItem.this;
		}

		@Override
		public void perform() {
			AbstractMarkdomListItem.this.listBlock = listBlock;
		}

	}

	AbstractMarkdomListItem(MarkdomFactory factory) {
		super(factory);
	}

	private MarkdomListBlock listBlock;

	@Override
	public final Optional<MarkdomListBlock> getParent() {
		return Optional.ofNullable(listBlock);
	}

	@Override
	public final Optional<Integer> getIndex() {
		return getParent().map(parent -> parent.getListItems().indexOf(this));
	}

	@Override
	public final Optional<MarkdomDocument> getDocument() {
		return getParent().flatMap(MarkdomListBlock::getDocument);
	}

	@Override
	public MarkdomBlockParentType getBlockParentType() {
		return MarkdomBlockParentType.LIST_ITEM;
	}

	@Override
	public final ManagedListItemAction onAttach(MarkdomListBlock listBlock) throws IllegalArgumentException, IllegalStateException {
		ObjectHelper.notNull("list block", listBlock);
		if (null != this.listBlock) {
			throw new IllegalStateException("This list item is already attached to a list block");
		}
		return new ManagedListItemAction(listBlock);
	}

	@Override
	public final ManagedListItemAction onDetach(MarkdomListBlock listBlock) throws IllegalStateException {
		ObjectHelper.notNull("list block", listBlock);
		if (null == this.listBlock) {
			throw new IllegalStateException("This list item is currently not attached to a list block");
		}
		if (this.listBlock != listBlock) {
			throw new IllegalStateException("This list item is not attached to the given list block");
		}
		return new ManagedListItemAction(null);
	}

	@Override
	public final void onHandle(MarkdomHandler<?> handler) {
		handler.onListItemBegin();
		doHandle(handler);
		handler.onListItemEnd();
	}

	protected abstract void doHandle(MarkdomHandler<?> handler);

}
