package io.markdom.model.basic;

import io.markdom.model.MarkdomListBlock;
import io.markdom.model.MarkdomListItem;
import io.markdom.util.ObjectHelper;

final class BasicMarkdomListItemList extends AbstractManagedList<MarkdomListItem, ManagedMarkdomListItem> {

	private final MarkdomListBlock parent;

	public BasicMarkdomListItemList(MarkdomListBlock parent) {
		this.parent = parent;
	}

	@Override
	protected AfterInsertAction<ManagedMarkdomListItem> beforeInsert(MarkdomListItem listItem) {
		return BasicMarkdomFactory.checkListItem(ObjectHelper.notNull("list item", listItem)).onAttach(parent);
	}

	@Override
	protected AfterRemoveAction<ManagedMarkdomListItem> beforeRemove(MarkdomListItem listItem) {
		return BasicMarkdomFactory.checkListItem(ObjectHelper.notNull("list item", listItem)).onDetach(parent);
	}

}
