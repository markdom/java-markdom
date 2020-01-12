package io.markdom.model;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentParentType;
import io.markdom.model.selection.MarkdomBlockSelection;
import io.markdom.model.selection.MarkdomContentParentSelection;

public interface MarkdomParagraphBlock extends MarkdomContentParentBlock {

	@Override
	public default MarkdomContentParentType getContentParentType() {
		return MarkdomContentParentType.PARAGRAPH_BLOCK;
	}

	@Override
	public default MarkdomBlockType getBlockType() {
		return MarkdomBlockType.PARAGRAPH;
	}

	@Override
	public MarkdomParagraphBlock addContent(MarkdomContent content);

	@Override
	public MarkdomParagraphBlock addContents(MarkdomContent... contents);

	@Override
	public MarkdomParagraphBlock addContents(Iterable<MarkdomContent> contents);

	@Override
	public default <Result> Result select(MarkdomBlockSelection<Result> selection) {
		return selection.onParagraphBlock(this);
	}

	@Override
	public default <Result> Result select(MarkdomContentParentSelection<Result> selection) {
		return selection.onParagraphBlock(this);
	}

}
