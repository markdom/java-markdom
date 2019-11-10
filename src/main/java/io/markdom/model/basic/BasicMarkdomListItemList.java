package io.markdom.model.basic;

import io.markdom.model.ManagedMarkdomListItem;
import io.markdom.model.MarkdomListBlock;
import io.markdom.util.ObjectHelper;

final class BasicMarkdomListItemList extends AbstractObservableList<ManagedMarkdomListItem> {

	private final MarkdomListBlock parent;

	public BasicMarkdomListItemList(MarkdomListBlock parent) {
		this.parent = parent;
	}

	@Override
	protected Runnable beforeInsert(ManagedMarkdomListItem listItem) {
		return ObjectHelper.notNull("list item", listItem).onAttach(parent);
	}

	@Override
	protected Runnable beforeRemove(ManagedMarkdomListItem listItem) {
		return ObjectHelper.notNull("list item", listItem).onDetach(parent);
	}
}
