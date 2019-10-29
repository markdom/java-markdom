package io.markdom.model;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentParentType;

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

}
