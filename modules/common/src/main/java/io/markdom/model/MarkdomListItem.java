package io.markdom.model;

import io.markdom.common.MarkdomBlockParentType;
import io.markdom.common.MarkdomNodeType;
import io.markdom.model.selection.MarkdomBlockParentSelection;
import io.markdom.model.selection.MarkdomNodeSelection;

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

	@Override
	public default <Result> Result select(MarkdomNodeSelection<Result> selection) {
		return selection.onListItem(this);
	}

	@Override
	public default <Result> Result select(MarkdomBlockParentSelection<Result> selection) {
		return selection.onListItem(this);
	}

}
