package io.markdom.model.basic;

import java.util.List;

import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomHeadingBlock;

public final class BasicMarkdomHeadingBlock extends AbstractMarkdomBlock implements MarkdomHeadingBlock {

	private final BasicMarkdomContentParentDelegate delegate = new BasicMarkdomContentParentDelegate(this);

	private MarkdomHeadingLevel level;

	public BasicMarkdomHeadingBlock(MarkdomFactory factory, MarkdomHeadingLevel level) {
		super(factory);
		setLevel(level);
	}

	@Override
	public MarkdomHeadingLevel getLevel() {
		return level;
	}

	public BasicMarkdomHeadingBlock setLevel(MarkdomHeadingLevel level) {
		if (null == level) {
			throw new IllegalArgumentException("The given level is null");
		}
		this.level = level;
		return this;
	}

	@Override
	public List<? extends MarkdomContent> getContents() {
		return delegate.getContents();
	}

	@Override
	public MarkdomHeadingBlock addContent(MarkdomContent content) {
		delegate.addContent(content);
		return this;
	}

	@Override
	public MarkdomHeadingBlock addContents(MarkdomContent... contents) {
		delegate.addContents(contents);
		return this;
	}

	@Override
	public MarkdomHeadingBlock addContents(Iterable<MarkdomContent> contents) {
		delegate.addContents(contents);
		return this;
	}

	@Override
	protected void doHandle(MarkdomHandler<?> handler) {
		handler.onHeadingBlockBegin(level);
		delegate.onHandle(handler);
		handler.onHeadingBlockEnd(level);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [level=" + level + ", content=" + getContents() + "]";
	}

}
