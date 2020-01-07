package io.markdom.model.selection;

import io.markdom.model.MarkdomCodeBlock;
import io.markdom.model.MarkdomCommentBlock;
import io.markdom.model.MarkdomDivisionBlock;
import io.markdom.model.MarkdomHeadingBlock;
import io.markdom.model.MarkdomOrderedListBlock;
import io.markdom.model.MarkdomParagraphBlock;
import io.markdom.model.MarkdomQuoteBlock;
import io.markdom.model.MarkdomUnorderedListBlock;

public interface MarkdomBlockSelection<Result> {

	public Result onCodeBlock(MarkdomCodeBlock codeBlock);

	public Result onCommentBlock(MarkdomCommentBlock commentBlock);

	public Result onDivisionBlock(MarkdomDivisionBlock divisionBlock);

	public Result onHeadingBlock(MarkdomHeadingBlock headingBlock);

	public Result onOrderedListBlock(MarkdomOrderedListBlock orderedListBlock);

	public Result onParagraphBlock(MarkdomParagraphBlock paragraphBlock);

	public Result onQuoteBlock(MarkdomQuoteBlock quoteBlock);

	public Result onUnorderedListBlock(MarkdomUnorderedListBlock unorderedListBlock);

}
