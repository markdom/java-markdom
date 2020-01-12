package io.markdom.model.basic;

import java.util.Optional;

import io.markdom.common.MarkdomContentType;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomContentParent;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.AbstractManagedList.AfterInsertAction;
import io.markdom.model.basic.AbstractManagedList.AfterRemoveAction;
import io.markdom.util.ObjectHelper;
import lombok.AllArgsConstructor;

abstract class AbstractMarkdomContent extends AbstractMarkdomNode implements ManagedMarkdomContent {

	@AllArgsConstructor
	private class ManagedContentAction implements AfterInsertAction<ManagedMarkdomContent>, AfterRemoveAction<ManagedMarkdomContent> {

		private final MarkdomContentParent parent;

		@Override
		public ManagedMarkdomContent getManagedPayload() {
			return AbstractMarkdomContent.this;
		}

		@Override
		public void perform() {
			AbstractMarkdomContent.this.parent = parent;
		}

	}

	AbstractMarkdomContent(MarkdomFactory factory) {
		super(factory);
	}

	private MarkdomContentParent parent;

	@Override
	public final boolean hasParent() {
		return null != parent;
	}

	@Override
	public final Optional<MarkdomContentParent> getParent() {
		return Optional.ofNullable(parent);
	}

	@Override
	public final Optional<Integer> getIndex() {
		return getParent().map(parent -> parent.getContents().indexOf(this));
	}

	@Override
	public final Optional<MarkdomDocument> getDocument() {
		return getParent().flatMap(MarkdomContentParent::getDocument);
	}

	@Override
	public final ManagedContentAction onAttach(MarkdomContentParent parent) {
		ObjectHelper.notNull("content parent", parent);
		if (null != this.parent) {
			throw new IllegalStateException("This content is already attached to a content parent");
		}
		return new ManagedContentAction(parent);
	}

	@Override
	public final ManagedContentAction onDetach(MarkdomContentParent parent) {
		ObjectHelper.notNull("content parent", parent);
		if (null == this.parent) {
			throw new IllegalStateException("This content is currently not attached to a content parent");
		}
		if (this.parent != parent) {
			throw new IllegalStateException("This content is not attached to the given content parent");
		}
		return new ManagedContentAction(null);
	}

	@Override
	public final void onHandle(MarkdomHandler<?> handler) {
		MarkdomContentType type = getContentType();
		handler.onContentBegin(type);
		doHandle(handler);
		handler.onContentEnd(type);
	}

	protected abstract void doHandle(MarkdomHandler<?> handler);

}
