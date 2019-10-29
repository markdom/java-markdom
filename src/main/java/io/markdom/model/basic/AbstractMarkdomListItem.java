package io.markdom.model.basic;

import java.util.Optional;

import io.markdom.common.MarkdomBlockParentType;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomListBlock;
import io.markdom.model.MarkdomListItem;

public abstract class AbstractMarkdomListItem extends AbstractMarkdomNode implements MarkdomListItem {

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

	public final void onAttach(MarkdomListBlock listBlock) throws IllegalArgumentException, IllegalStateException {
		if (null == listBlock) {
			throw new IllegalArgumentException("The given Markdom list block is null");
		}
		if (null != this.listBlock) {
			throw new IllegalStateException("This Markdom  list item is already attached to a list block");
		}
		this.listBlock = listBlock;
	}

	public final void onDetach() throws IllegalStateException {
		if (null == this.listBlock) {
			throw new IllegalStateException("This Markdom list item is currently not attached to a list block");
		}
		this.listBlock = null;
	}

	public final void onHandle(MarkdomHandler<?> handler) {
		handler.onListItemBegin();
		doHandle(handler);
		handler.onListItemEnd();
	}

	protected abstract void doHandle(MarkdomHandler<?> handler);

}
