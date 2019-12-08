package io.markdom.model.basic;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomBlock;
import io.markdom.model.MarkdomBlockParent;
import io.markdom.model.basic.AbstractManagedList.AfterInsertAction;
import io.markdom.model.basic.AbstractManagedList.AfterRemoveAction;

public interface ManagedMarkdomBlock extends MarkdomBlock {

	public AfterInsertAction<ManagedMarkdomBlock> onAttach(MarkdomBlockParent parent);

	public AfterRemoveAction<ManagedMarkdomBlock> onDetach(MarkdomBlockParent parent);

	public void onHandle(MarkdomHandler<?> handler);

}