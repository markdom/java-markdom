package io.markdom.model.selection;

import io.markdom.model.MarkdomEmphasisContent;
import io.markdom.model.MarkdomHeadingBlock;
import io.markdom.model.MarkdomLinkContent;
import io.markdom.model.MarkdomParagraphBlock;

public interface MarkdomContentParentSelection<Result> {

	public Result onHeadingBlock(MarkdomHeadingBlock headingBlock);

	public Result onParagraphBlock(MarkdomParagraphBlock paragraphBlock);

	public Result onEmphasisContent(MarkdomEmphasisContent emphasisContent);

	public Result onLinkContent(MarkdomLinkContent linkContent);

}
