package io.markdom.model.basic;

import io.markdom.model.ManagedMarkdomBlock;
import io.markdom.model.MarkdomBlockParent;

final class BasicMarkdomBlockList extends AbstractObservableList<ManagedMarkdomBlock> {

	private final MarkdomBlockParent parent;

	public BasicMarkdomBlockList(MarkdomBlockParent parent) {
		this.parent = parent;
	}

	@Override
	protected Runnable beforeInsert(ManagedMarkdomBlock block) {
		if (null == block) {
			throw new IllegalArgumentException("The given Markdom block is null");
		}
		return block.onAttach(parent);
	}

	@Override
	protected Runnable beforeRemove(ManagedMarkdomBlock block) {
		if (null == block) {
			throw new IllegalArgumentException("The given Markdom block is null");
		}
		return block.onDetach(parent);
	}

}
