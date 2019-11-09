package io.markdom.model;

import io.markdom.common.MarkdomBlockType;
import io.markdom.model.selection.MarkdomBlockSelection;

public interface MarkdomCommentBlock extends MarkdomBlock {

	@Override
	public default MarkdomBlockType getBlockType() {
		return MarkdomBlockType.COMMENT;
	}

	public String getComment();

	public MarkdomCommentBlock setComment(String comment);

	@Override
	public default <Result> Result select(MarkdomBlockSelection<Result> selection) {
		return selection.onCommentBlock(this);
	}

}
