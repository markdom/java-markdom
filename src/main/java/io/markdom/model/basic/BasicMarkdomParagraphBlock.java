package io.markdom.model.basic;

import java.util.List;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomParagraphBlock;

public final class BasicMarkdomParagraphBlock extends AbstractMarkdomBlock implements MarkdomParagraphBlock {

	private final BasicMarkdomContentParentDelegate delegate = new BasicMarkdomContentParentDelegate(this);

	public BasicMarkdomParagraphBlock(MarkdomFactory factory) {
		super(factory);
	}

	@Override
	public List<? extends MarkdomContent> getContents() {
		return delegate.getContents();
	}

	@Override
	public MarkdomParagraphBlock addContent(MarkdomContent content) {
		delegate.addContent(content);
		return this;
	}

	@Override
	public MarkdomParagraphBlock addContents(MarkdomContent... contents) {
		delegate.addContents(contents);
		return this;
	}

	@Override
	public MarkdomParagraphBlock addContents(Iterable<MarkdomContent> contents) {
		delegate.addContents(contents);
		return this;
	}

	@Override
	protected void doHandle(MarkdomHandler<?> handler) {
		handler.onParagraphBlockBegin();
		delegate.onHandle(handler);
		handler.onParagraphBlockEnd();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [content=" + getContents() + "]";
	}

}
