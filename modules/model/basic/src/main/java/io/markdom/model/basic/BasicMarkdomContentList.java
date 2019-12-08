package io.markdom.model.basic;

import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomContentParent;
import io.markdom.util.ObjectHelper;

final class BasicMarkdomContentList extends AbstractManagedList<MarkdomContent, ManagedMarkdomContent> {

	private final MarkdomContentParent parent;

	public BasicMarkdomContentList(MarkdomContentParent parent) {
		this.parent = parent;
	}

	@Override
	protected AfterInsertAction<ManagedMarkdomContent> beforeInsert(MarkdomContent content) {
		return BasicMarkdomFactory.checkContent(ObjectHelper.notNull("content", content)).onAttach(parent);
	}

	@Override
	protected AfterRemoveAction<ManagedMarkdomContent> beforeRemove(MarkdomContent content) {
		return BasicMarkdomFactory.checkContent(ObjectHelper.notNull("content", content)).onDetach(parent);
	}
	
}
