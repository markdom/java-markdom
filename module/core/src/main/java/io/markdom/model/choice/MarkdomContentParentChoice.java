package io.markdom.model.choice;

import io.markdom.model.MarkdomEmphasisContent;
import io.markdom.model.MarkdomHeadingBlock;
import io.markdom.model.MarkdomLinkContent;
import io.markdom.model.MarkdomParagraphBlock;

public interface MarkdomContentParentChoice {

	public void onHeadingBlock(MarkdomHeadingBlock headingBlock);

	public void onParagraphBlock(MarkdomParagraphBlock paragraphBlock);

	public void onEmphasisContent(MarkdomEmphasisContent emphasisContent);

	public void onLinkContent(MarkdomLinkContent linkContent);

}
