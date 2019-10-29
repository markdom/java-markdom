package io.markdom.model;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentParentType;
import io.markdom.common.MarkdomHeadingLevel;

public interface MarkdomHeadingBlock extends MarkdomContentParentBlock {

	@Override
	public default MarkdomContentParentType getContentParentType() {
		return MarkdomContentParentType.HEADING_BLOCK;
	}

	@Override
	public default MarkdomBlockType getBlockType() {
		return MarkdomBlockType.HEADING;
	}

	public MarkdomHeadingLevel getLevel();

	public MarkdomHeadingBlock setLevel(MarkdomHeadingLevel level);

	@Override
	public MarkdomHeadingBlock addContent(MarkdomContent content);

	@Override
	public MarkdomHeadingBlock addContents(MarkdomContent... contents);

	@Override
	public MarkdomHeadingBlock addContents(Iterable<MarkdomContent> contents);

}
