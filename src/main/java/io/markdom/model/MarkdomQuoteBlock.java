package io.markdom.model;

import io.markdom.common.MarkdomBlockParentType;
import io.markdom.common.MarkdomBlockType;
import io.markdom.model.selection.MarkdomBlockParentSelection;
import io.markdom.model.selection.MarkdomBlockSelection;

public interface MarkdomQuoteBlock extends MarkdomBlock, MarkdomBlockParent {

	@Override
	public default MarkdomBlockParentType getBlockParentType() {
		return MarkdomBlockParentType.QUOTE_BLOCK;
	}

	@Override
	public default MarkdomBlockType getBlockType() {
		return MarkdomBlockType.QUOTE;
	}

	@Override
	public MarkdomQuoteBlock addBlock(MarkdomBlock block);

	@Override
	public MarkdomQuoteBlock addBlocks(MarkdomBlock... blocks);

	@Override
	public MarkdomQuoteBlock addBlocks(Iterable<MarkdomBlock> blocks);

	@Override
	public default <Result> Result select(MarkdomBlockSelection<Result> selection) {
		return selection.onQuoteBlock(this);
	}

	@Override
	public default <Result> Result select(MarkdomBlockParentSelection<Result> selection) {
		return selection.onQuoteBlock(this);
	}

}
