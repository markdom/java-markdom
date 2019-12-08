package io.markdom.model;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentParentType;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.model.selection.MarkdomBlockSelection;
import io.markdom.model.selection.MarkdomContentParentSelection;

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

	@Override
	public default <Result> Result select(MarkdomBlockSelection<Result> selection) {
		return selection.onHeadingBlock(this);
	}

	@Override
	public default <Result> Result select(MarkdomContentParentSelection<Result> selection) {
		return selection.onHeadingBlock(this);
	}

}
