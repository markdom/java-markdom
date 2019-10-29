package io.markdom.model.basic;

import io.markdom.model.ManagedMarkdomContent;
import io.markdom.model.MarkdomContentParent;

final class BasicMarkdomContentList extends AbstractObservableList<ManagedMarkdomContent> {

	private final MarkdomContentParent parent;

	public BasicMarkdomContentList(MarkdomContentParent parent) {
		this.parent = parent;
	}

	@Override
	protected void beforeInsert(ManagedMarkdomContent content) {
		if (null == content) {
			throw new IllegalArgumentException("The given Markdom content is null");
		}
	}

	@Override
	protected void onInsert(ManagedMarkdomContent content) {
		content.onAttach(parent);
	}

	@Override
	protected void onRemove(ManagedMarkdomContent content) {
		content.onDetach();
	}

}
