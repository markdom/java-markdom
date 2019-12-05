package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.markdom.common.MarkdomKeys;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomBlock;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomQuoteBlock;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Property;

public final class BasicMarkdomQuoteBlock extends AbstractMarkdomBlock implements MarkdomQuoteBlock {

	// @formatter:off
	private static final List<Property<MarkdomQuoteBlock, ?>> PROPERTIES = new ArrayList<>(Arrays.asList(
		new Property<>(MarkdomKeys.BLOCKS, MarkdomQuoteBlock::getBlocks)
	));
	// @formatter:on	

	private final BasicMarkdomBlockParentDelegate delegate = new BasicMarkdomBlockParentDelegate(this);

	public BasicMarkdomQuoteBlock(MarkdomFactory factory) {
		super(factory);
	}

	@Override
	public List<MarkdomBlock> getBlocks() {
		return delegate.getBlocks();
	}

	@Override
	public final BasicMarkdomQuoteBlock addBlock(MarkdomBlock block) {
		delegate.addBlock(block);
		return this;
	}

	@Override
	public final BasicMarkdomQuoteBlock addBlocks(MarkdomBlock... blocks) {
		delegate.addBlocks(blocks);
		return this;
	}

	@Override
	public final BasicMarkdomQuoteBlock addBlocks(Iterable<MarkdomBlock> blocks) {
		delegate.addBlocks(blocks);
		return this;
	}

	@Override
	public void doHandle(MarkdomHandler<?> handler) {
		handler.onQuoteBlockBegin();
		delegate.onHandle(handler);
		handler.onQuoteBlockEnd();
	}

	@Override
	public int hashCode() {
		return ObjectHelper.hashCode(this, PROPERTIES);
	}

	@Override
	public boolean equals(Object object) {
		return ObjectHelper.equals(this, MarkdomQuoteBlock.class, PROPERTIES, object);
	}

	@Override
	public String toString() {
		return ObjectHelper.toString(this, PROPERTIES);
	}

}
