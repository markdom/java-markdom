package io.markdom.model;

import io.markdom.common.MarkdomBlockType;

public interface MarkdomDivisionBlock extends MarkdomBlock {

	@Override
	public default MarkdomBlockType getBlockType() {
		return MarkdomBlockType.DIVISION;
	}

}
