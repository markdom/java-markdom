package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomKeys;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomEmphasisContent;
import io.markdom.model.MarkdomFactory;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Property;

public final class BasicMarkdomEmphasisContent extends AbstractMarkdomContent implements MarkdomEmphasisContent {

	// @formatter:off
	private static final List<Property<MarkdomEmphasisContent, ?>> PROPERTIES = new ArrayList<>(Arrays.asList(
		new Property<>(MarkdomKeys.LEVEL, MarkdomEmphasisContent::getLevel),
		new Property<>(MarkdomKeys.CONTENTS, MarkdomEmphasisContent::getContents)
	));
	// @formatter:on	

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
		this.level = ObjectHelper.notNull("level", level);
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
	public int hashCode() {
		return ObjectHelper.hashCode(this, PROPERTIES);
	}

	@Override
	public boolean equals(Object object) {
		return ObjectHelper.equals(this, MarkdomEmphasisContent.class, PROPERTIES, object);
	}

	@Override
	public String toString() {
		return ObjectHelper.toString(this, PROPERTIES);
	}

}
