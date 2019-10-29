package io.markdom.model;

import io.markdom.common.MarkdomBlockType;

public interface MarkdomCommentBlock extends MarkdomBlock {

	@Override
	public default MarkdomBlockType getBlockType() {
		return MarkdomBlockType.COMMENT;
	}

	public String getComment();

	public MarkdomCommentBlock setComment(String comment);

}
