package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.markdom.common.MarkdomKeys;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomBlock;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomListItem;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Property;

public final class BasicMarkdomListItem extends AbstractMarkdomListItem {

	// @formatter:off
	private static final List<Property<MarkdomListItem, ?>> PROPERTIES = new ArrayList<>(Arrays.asList(
		new Property<>(MarkdomKeys.BLOCKS, MarkdomListItem::getBlocks)
	));
	// @formatter:on	

	private final BasicMarkdomBlockParentDelegate delegate = new BasicMarkdomBlockParentDelegate(this);

	BasicMarkdomListItem(MarkdomFactory factory) {
		super(factory);
	}

	@Override
	public final List<MarkdomBlock> getBlocks() {
		return delegate.getBlocks();
	}

	@Override
	public final BasicMarkdomListItem addBlock(MarkdomBlock block) {
		delegate.addBlock(block);
		return this;
	}

	@Override
	public final BasicMarkdomListItem addBlocks(MarkdomBlock... blocks) {
		delegate.addBlocks(blocks);
		return this;
	}

	@Override
	public final BasicMarkdomListItem addBlocks(Iterable<MarkdomBlock> blocks) {
		delegate.addBlocks(blocks);
		return this;
	}

	@Override
	protected void doHandle(MarkdomHandler<?> handler) {
		delegate.onHandle(handler);
	}

	@Override
	public int hashCode() {
		return ObjectHelper.hashCode(this, PROPERTIES);
	}

	@Override
	public boolean equals(Object object) {
		return ObjectHelper.equals(this, MarkdomListItem.class, PROPERTIES, object);
	}

	@Override
	public String toString() {
		return ObjectHelper.toString(this, PROPERTIES);
	}

}
