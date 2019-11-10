package io.markdom.model.basic;

import io.markdom.model.ManagedMarkdomContent;
import io.markdom.model.MarkdomContentParent;
import io.markdom.util.ObjectHelper;

final class BasicMarkdomContentList extends AbstractObservableList<ManagedMarkdomContent> {

	private final MarkdomContentParent parent;

	public BasicMarkdomContentList(MarkdomContentParent parent) {
		this.parent = parent;
	}

	@Override
	protected Runnable beforeInsert(ManagedMarkdomContent content) {
		return ObjectHelper.notNull("content", content).onAttach(parent);
	}

	@Override
	protected Runnable beforeRemove(ManagedMarkdomContent content) {
		return ObjectHelper.notNull("content", content).onDetach(parent);
	}
}
