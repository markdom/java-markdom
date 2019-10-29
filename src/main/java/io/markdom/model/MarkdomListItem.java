package io.markdom.model;

import io.markdom.common.MarkdomBlockParentType;
import io.markdom.common.MarkdomNodeType;

public interface MarkdomListItem extends MarkdomBlockParent {

		
	@Override
	public default MarkdomNodeType getNodeType() {
		return MarkdomNodeType.LIST_ITEM;
	}

	@Override
	public default MarkdomBlockParentType getBlockParentType() {
		return MarkdomBlockParentType.LIST_ITEM;
	}

	@Override
	public MarkdomListItem addBlock(MarkdomBlock block);

	@Override
	public MarkdomListItem addBlocks(MarkdomBlock... blocks);

	@Override
	public MarkdomListItem addBlocks(Iterable<MarkdomBlock> blocks);

}
