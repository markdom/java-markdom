package io.markdom.model.choice;

import io.markdom.model.MarkdomCodeBlock;
import io.markdom.model.MarkdomCommentBlock;
import io.markdom.model.MarkdomDivisionBlock;
import io.markdom.model.MarkdomHeadingBlock;
import io.markdom.model.MarkdomOrderedListBlock;
import io.markdom.model.MarkdomParagraphBlock;
import io.markdom.model.MarkdomQuoteBlock;
import io.markdom.model.MarkdomUnorderedListBlock;

public abstract class AbstractMarkdomBlockChoice implements MarkdomBlockChoice {

	@Override
	public void onCodeBlock(MarkdomCodeBlock codeBlock) {
	}

	@Override
	public void onCommentBlock(MarkdomCommentBlock commentBlock) {
	}

	@Override
	public void onDivisionBlock(MarkdomDivisionBlock divisionBlock) {
	}

	@Override
	public void onHeadingBlock(MarkdomHeadingBlock headingBlock) {
	}

	@Override
	public void onOrderedListBlock(MarkdomOrderedListBlock orderedListBlock) {
	}

	@Override
	public void onParagraphBlock(MarkdomParagraphBlock paragraphBlock) {
	}

	@Override
	public void onQuoteBlock(MarkdomQuoteBlock quoteBlock) {
	}

	@Override
	public void onUnorderedListBlock(MarkdomUnorderedListBlock unorderedListBlock) {
	}

}
