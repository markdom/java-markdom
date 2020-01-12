package io.markdom.model.selection;

import io.markdom.model.MarkdomEmphasisContent;
import io.markdom.model.MarkdomHeadingBlock;
import io.markdom.model.MarkdomLinkContent;
import io.markdom.model.MarkdomParagraphBlock;

public abstract class AbstractMarkdomContentParentSelection<Result> implements MarkdomContentParentSelection<Result> {

	@Override
	public Result onHeadingBlock(MarkdomHeadingBlock headingBlock) {
		return null;
	}

	@Override
	public Result onParagraphBlock(MarkdomParagraphBlock paragraphBlock) {
		return null;
	}

	@Override
	public Result onEmphasisContent(MarkdomEmphasisContent emphasisContent) {
		return null;
	}

	@Override
	public Result onLinkContent(MarkdomLinkContent linkContent) {
		return null;
	}

}
