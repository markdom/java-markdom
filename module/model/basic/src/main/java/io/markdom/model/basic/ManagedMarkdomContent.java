package io.markdom.model.basic;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomContentParent;
import io.markdom.model.basic.AbstractManagedList.AfterInsertAction;
import io.markdom.model.basic.AbstractManagedList.AfterRemoveAction;

public interface ManagedMarkdomContent extends MarkdomContent {

	public AfterInsertAction<ManagedMarkdomContent> onAttach(MarkdomContentParent parent);

	public AfterRemoveAction<ManagedMarkdomContent> onDetach(MarkdomContentParent parent);

	public void onHandle(MarkdomHandler<?> handler);

}