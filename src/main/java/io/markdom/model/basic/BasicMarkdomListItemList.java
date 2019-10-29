package io.markdom.model.basic;

import io.markdom.model.ManagedMarkdomListItem;
import io.markdom.model.MarkdomListBlock;

final class BasicMarkdomListItemList extends AbstractObservableList<ManagedMarkdomListItem> {

	private final MarkdomListBlock parent;

	public BasicMarkdomListItemList(MarkdomListBlock parent) {
		this.parent = parent;
	}

	@Override
	protected void beforeInsert(ManagedMarkdomListItem listItem) {
		if (null == listItem) {
			throw new IllegalArgumentException("The given Markdom list item is null");
		}
	}

	@Override
	protected void onInsert(ManagedMarkdomListItem listItem) {
		listItem.onAttach(parent);
	}

	@Override
	protected void onRemove(ManagedMarkdomListItem listItem) {
		listItem.onDetach();
	}

}
