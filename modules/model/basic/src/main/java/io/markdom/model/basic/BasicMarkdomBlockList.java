package io.markdom.model.basic;

import io.markdom.model.MarkdomBlock;
import io.markdom.model.MarkdomBlockParent;
import io.markdom.util.ObjectHelper;

final class BasicMarkdomBlockList extends AbstractManagedList<MarkdomBlock, ManagedMarkdomBlock> {

	private final MarkdomBlockParent parent;

	public BasicMarkdomBlockList(MarkdomBlockParent parent) {
		this.parent = parent;
	}

	@Override
	protected AfterInsertAction<ManagedMarkdomBlock> beforeInsert(MarkdomBlock block) {
		return BasicMarkdomFactory.checkBlock(ObjectHelper.notNull("block", block)).onAttach(parent);
	}

	@Override
	protected AfterRemoveAction<ManagedMarkdomBlock> beforeRemove(MarkdomBlock block) {
		return BasicMarkdomFactory.checkBlock(ObjectHelper.notNull("block", block)).onDetach(parent);
	}

}
