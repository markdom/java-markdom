package io.markdom.model.basic;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomListBlock;
import io.markdom.model.MarkdomListItem;
import io.markdom.model.basic.AbstractManagedList.AfterInsertAction;
import io.markdom.model.basic.AbstractManagedList.AfterRemoveAction;

public interface ManagedMarkdomListItem extends MarkdomListItem {

	public AfterInsertAction<ManagedMarkdomListItem> onAttach(MarkdomListBlock listBlock);

	public AfterRemoveAction<ManagedMarkdomListItem> onDetach(MarkdomListBlock listBlock);

	public void onHandle(MarkdomHandler<?> handler);

}