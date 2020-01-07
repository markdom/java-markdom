package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.markdom.common.MarkdomKeys;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomParagraphBlock;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Property;

public final class BasicMarkdomParagraphBlock extends AbstractMarkdomBlock implements MarkdomParagraphBlock {

	// @formatter:off
	private static final List<Property<MarkdomParagraphBlock>> PROPERTIES = new ArrayList<>(Arrays.asList(
		new Property<>(MarkdomKeys.CONTENTS, MarkdomParagraphBlock::getContents)
	));
	// @formatter:on	

	private final BasicMarkdomContentParentDelegate delegate = new BasicMarkdomContentParentDelegate(this);

	public BasicMarkdomParagraphBlock(MarkdomFactory factory) {
		super(factory);
	}

	@Override
	public List<MarkdomContent> getContents() {
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
	public int hashCode() {
		return ObjectHelper.hashCode(this, PROPERTIES);
	}

	@Override
	public boolean equals(Object object) {
		return ObjectHelper.equals(this, MarkdomParagraphBlock.class, PROPERTIES, object);
	}

	@Override
	public String toString() {
		return ObjectHelper.toString(this, PROPERTIES);
	}

}
