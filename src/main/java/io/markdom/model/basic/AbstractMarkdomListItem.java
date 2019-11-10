package io.markdom.model.basic;

import java.util.Optional;

import io.markdom.common.MarkdomBlockParentType;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.ManagedMarkdomListItem;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomListBlock;
import io.markdom.util.ObjectHelper;

public abstract class AbstractMarkdomListItem extends AbstractMarkdomNode implements ManagedMarkdomListItem {

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
	public final Runnable onAttach(MarkdomListBlock listBlock) throws IllegalArgumentException, IllegalStateException {
		ObjectHelper.notNull("list block", listBlock);
		if (null != this.listBlock) {
			throw new IllegalStateException("This list item is already attached to a list block");
		}
		return () -> AbstractMarkdomListItem.this.listBlock = listBlock;
	}

	@Override
	public final Runnable onDetach(MarkdomListBlock listBlock) throws IllegalStateException {
		ObjectHelper.notNull("list block", listBlock);
		if (null == this.listBlock) {
			throw new IllegalStateException("This list item is currently not attached to a list block");
		}
		if (this.listBlock != listBlock) {
			throw new IllegalStateException("This list item is not attached to the given list block");
		}
		return () -> AbstractMarkdomListItem.this.listBlock = null;
	}

	@Override
	public final void onHandle(MarkdomHandler<?> handler) {
		handler.onListItemBegin();
		doHandle(handler);
		handler.onListItemEnd();
	}

	protected abstract void doHandle(MarkdomHandler<?> handler);

}
