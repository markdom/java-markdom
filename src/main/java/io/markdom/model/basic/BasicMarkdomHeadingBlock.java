package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.common.MarkdomKeys;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomHeadingBlock;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Property;

public final class BasicMarkdomHeadingBlock extends AbstractMarkdomBlock implements MarkdomHeadingBlock {

	// @formatter:off
	private static final List<Property<MarkdomHeadingBlock, ?>> PROPERTIES = new ArrayList<>(Arrays.asList(
		new Property<>(MarkdomKeys.LEVEL, MarkdomHeadingBlock::getLevel),
		new Property<>(MarkdomKeys.CONTENTS, MarkdomHeadingBlock::getContents)
	));
	// @formatter:on	

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
	public int hashCode() {
		return ObjectHelper.hashCode(this, PROPERTIES);
	}

	@Override
	public boolean equals(Object object) {
		return ObjectHelper.equals(this, MarkdomHeadingBlock.class, PROPERTIES, object);
	}

	@Override
	public String toString() {
		return ObjectHelper.toString(this, PROPERTIES);
	}

}
