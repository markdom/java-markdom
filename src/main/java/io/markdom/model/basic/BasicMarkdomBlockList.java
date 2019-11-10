package io.markdom.model.basic;

import io.markdom.model.ManagedMarkdomBlock;
import io.markdom.model.MarkdomBlockParent;
import io.markdom.util.ObjectHelper;

final class BasicMarkdomBlockList extends AbstractObservableList<ManagedMarkdomBlock> {

	private final MarkdomBlockParent parent;

	public BasicMarkdomBlockList(MarkdomBlockParent parent) {
		this.parent = parent;
	}

	@Override
	protected Runnable beforeInsert(ManagedMarkdomBlock block) {
		return ObjectHelper.notNull("block", block).onAttach(parent);
	}

	@Override
	protected Runnable beforeRemove(ManagedMarkdomBlock block) {
		return ObjectHelper.notNull("block", block).onDetach(parent);
	}

}
