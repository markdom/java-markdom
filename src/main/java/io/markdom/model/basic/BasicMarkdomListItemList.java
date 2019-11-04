package io.markdom.model.basic;

import io.markdom.model.ManagedMarkdomListItem;
import io.markdom.model.MarkdomListBlock;

final class BasicMarkdomListItemList extends AbstractObservableList<ManagedMarkdomListItem> {

	private final MarkdomListBlock parent;

	public BasicMarkdomListItemList(MarkdomListBlock parent) {
		this.parent = parent;
	}

	@Override
	protected Runnable beforeInsert(ManagedMarkdomListItem listItem) {
		if (null == listItem) {
			throw new IllegalArgumentException("The given Markdom list item is null");
		}
		return listItem.onAttach(parent);
	}

	@Override
	protected Runnable beforeRemove(ManagedMarkdomListItem listItem) {
		if (null == listItem) {
			throw new IllegalArgumentException("The given Markdom list item is null");
		}
		return listItem.onDetach(parent);
	}
}
