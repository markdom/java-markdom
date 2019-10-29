package io.markdom.model.basic;

import java.util.List;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomEmphasisContent;
import io.markdom.model.MarkdomFactory;

public final class BasicMarkdomEmphasisContent extends AbstractMarkdomContent implements MarkdomEmphasisContent {

	private final BasicMarkdomContentParentDelegate delegate = new BasicMarkdomContentParentDelegate(this);

	private MarkdomEmphasisLevel level;

	public BasicMarkdomEmphasisContent(MarkdomFactory factory, MarkdomEmphasisLevel level) {
		super(factory);
		setLevel(level);
	}

	@Override
	public MarkdomEmphasisLevel getLevel() {
		return level;
	}

	public BasicMarkdomEmphasisContent setLevel(MarkdomEmphasisLevel level) {
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
	public MarkdomEmphasisContent addContent(MarkdomContent content) {
		delegate.addContent(content);
		return this;
	}

	@Override
	public MarkdomEmphasisContent addContents(MarkdomContent... contents) {
		delegate.addContents(contents);
		return this;
	}

	@Override
	public MarkdomEmphasisContent addContents(Iterable<MarkdomContent> contents) {
		delegate.addContents(contents);
		return this;
	}

	@Override
	protected void doHandle(MarkdomHandler<?> handler) {
		handler.onEmphasisContentBegin(level);
		delegate.onHandle(handler);
		handler.onEmphasisContentEnd(level);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [level=" + level + ", content=" + getContents() + "]";
	}

}
