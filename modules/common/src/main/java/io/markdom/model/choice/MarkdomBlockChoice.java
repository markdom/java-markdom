package io.markdom.model.choice;

import io.markdom.model.MarkdomCodeBlock;
import io.markdom.model.MarkdomCommentBlock;
import io.markdom.model.MarkdomDivisionBlock;
import io.markdom.model.MarkdomHeadingBlock;
import io.markdom.model.MarkdomOrderedListBlock;
import io.markdom.model.MarkdomParagraphBlock;
import io.markdom.model.MarkdomQuoteBlock;
import io.markdom.model.MarkdomUnorderedListBlock;

public interface MarkdomBlockChoice {

	public void onCodeBlock(MarkdomCodeBlock codeBlock);

	public void onCommentBlock(MarkdomCommentBlock commentBlock);

	public void onDivisionBlock(MarkdomDivisionBlock divisionBlock);

	public void onHeadingBlock(MarkdomHeadingBlock headingBlock);

	public void onOrderedListBlock(MarkdomOrderedListBlock orderedListBlock);

	public void onParagraphBlock(MarkdomParagraphBlock paragraphBlock);

	public void onQuoteBlock(MarkdomQuoteBlock quoteBlock);

	public void onUnorderedListBlock(MarkdomUnorderedListBlock unorderedListBlock);

}
