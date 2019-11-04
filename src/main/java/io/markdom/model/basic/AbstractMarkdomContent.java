package io.markdom.model.basic;

import java.util.Optional;

import io.markdom.common.MarkdomContentType;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.ManagedMarkdomContent;
import io.markdom.model.MarkdomContentParent;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;

abstract class AbstractMarkdomContent extends AbstractMarkdomNode implements ManagedMarkdomContent {

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
	public final Runnable onAttach(MarkdomContentParent parent) {
		if (null == parent) {
			throw new IllegalArgumentException("The given Markdom content parent is null");
		}
		if (null != this.parent) {
			throw new IllegalStateException("This Markdom content is already attached to a content parent");
		}
		return () -> AbstractMarkdomContent.this.parent = parent;
	}

	@Override
	public final Runnable onDetach(MarkdomContentParent parent) {
		if (null == this.parent) {
			throw new IllegalStateException("This Markdom content is currently not attached to a content parent");
		}
		if (this.parent != parent) {
			throw new IllegalStateException("This Markdom content is not attached to teh given content parent");
		}
		return () -> AbstractMarkdomContent.this.parent = null;
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
