package io.markdom.model.choice;

import io.markdom.model.MarkdomEmphasisContent;
import io.markdom.model.MarkdomHeadingBlock;
import io.markdom.model.MarkdomLinkContent;
import io.markdom.model.MarkdomParagraphBlock;

public abstract class AbstractMarkdomContentParentChoice implements MarkdomContentParentChoice {

	@Override
	public void onHeadingBlock(MarkdomHeadingBlock headingBlock) {
	}

	@Override
	public void onParagraphBlock(MarkdomParagraphBlock paragraphBlock) {
	}

	@Override
	public void onEmphasisContent(MarkdomEmphasisContent emphasisContent) {
	}

	@Override
	public void onLinkContent(MarkdomLinkContent linkContent) {
	}

}
