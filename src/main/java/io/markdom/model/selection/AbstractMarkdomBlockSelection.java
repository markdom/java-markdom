package io.markdom.model.selection;

import io.markdom.model.MarkdomCodeBlock;
import io.markdom.model.MarkdomCommentBlock;
import io.markdom.model.MarkdomDivisionBlock;
import io.markdom.model.MarkdomHeadingBlock;
import io.markdom.model.MarkdomOrderedListBlock;
import io.markdom.model.MarkdomParagraphBlock;
import io.markdom.model.MarkdomQuoteBlock;
import io.markdom.model.MarkdomUnorderedListBlock;

public abstract class AbstractMarkdomBlockSelection<Result> implements MarkdomBlockSelection<Result> {

	@Override
	public Result onCodeBlock(MarkdomCodeBlock codeBlock) {
		return null;
	}

	@Override
	public Result onCommentBlock(MarkdomCommentBlock commentBlock) {
		return null;
	}

	@Override
	public Result onDivisionBlock(MarkdomDivisionBlock divisionBlock) {
		return null;
	}

	@Override
	public Result onHeadingBlock(MarkdomHeadingBlock headingBlock) {
		return null;
	}

	@Override
	public Result onOrderedListBlock(MarkdomOrderedListBlock orderedListBlock) {
		return null;
	}

	@Override
	public Result onParagraphBlock(MarkdomParagraphBlock paragraphBlock) {
		return null;
	}

	@Override
	public Result onQuoteBlock(MarkdomQuoteBlock quoteBlock) {
		return null;
	}

	@Override
	public Result onUnorderedListBlock(MarkdomUnorderedListBlock unorderedListBlock) {
		return null;
	}

}
