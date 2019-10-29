package io.markdom.model;

import io.markdom.common.MarkdomBlockParentType;
import io.markdom.common.MarkdomNodeType;
import io.markdom.handler.MarkdomDispatcher;

public interface MarkdomDocument extends MarkdomBlockParent, MarkdomDispatcher {

	@Override
	public default MarkdomNodeType getNodeType() {
		return MarkdomNodeType.DOCUMENT;
	}
	
	@Override
	public default MarkdomBlockParentType getBlockParentType() {
		return MarkdomBlockParentType.DOCUMENT;
	}

	@Override
	public MarkdomDocument addBlock(MarkdomBlock block);

	@Override
	public MarkdomDocument addBlocks(MarkdomBlock... blocks);

	@Override
	public MarkdomDocument addBlocks(Iterable<MarkdomBlock> blocks);

}
