package io.markdom.model.basic;

import io.markdom.model.ManagedMarkdomContent;
import io.markdom.model.MarkdomContentParent;

final class BasicMarkdomContentList extends AbstractObservableList<ManagedMarkdomContent> {

	private final MarkdomContentParent parent;

	public BasicMarkdomContentList(MarkdomContentParent parent) {
		this.parent = parent;
	}

	@Override
	protected Runnable beforeInsert(ManagedMarkdomContent content) {
		if (null == content) {
			throw new IllegalArgumentException("The given Markdom content is null");
		}
		return content.onAttach(parent);
	}

	@Override
	protected Runnable beforeRemove(ManagedMarkdomContent content) {
		if (null == content) {
			throw new IllegalArgumentException("The given Markdom content is null");
		}
		return content.onDetach(parent);
	}
}
