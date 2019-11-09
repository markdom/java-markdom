package io.markdom.model;

import io.markdom.common.MarkdomBlockType;
import io.markdom.model.selection.MarkdomBlockSelection;

public interface MarkdomDivisionBlock extends MarkdomBlock {

	@Override
	public default MarkdomBlockType getBlockType() {
		return MarkdomBlockType.DIVISION;
	}

	@Override
	public default <Result> Result select(MarkdomBlockSelection<Result> selection) {
		return selection.onDivisionBlock(this);
	}

}
