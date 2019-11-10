package io.markdom.model.selection;

import io.markdom.model.MarkdomCodeBlock;
import io.markdom.model.MarkdomCommentBlock;
import io.markdom.model.MarkdomDivisionBlock;
import io.markdom.model.MarkdomHeadingBlock;
import io.markdom.model.MarkdomOrderedListBlock;
import io.markdom.model.MarkdomParagraphBlock;
import io.markdom.model.MarkdomQuoteBlock;
import io.markdom.model.MarkdomUnorderedListBlock;
import io.markdom.model.choice.MarkdomBlockChoice;
import io.markdom.util.ObjectHelper;

public final class MarkdomBlockChoiceSelection extends AbstractMarkdomBlockSelection<Void> {

	private final MarkdomBlockChoice choice;

	public MarkdomBlockChoiceSelection(MarkdomBlockChoice choice) {
		this.choice = ObjectHelper.notNull("choice", choice);
	}

	@Override
	public Void onCodeBlock(MarkdomCodeBlock codeBlock) {
		choice.onCodeBlock(codeBlock);
		return null;
	}

	@Override
	public Void onCommentBlock(MarkdomCommentBlock commentBlock) {
		choice.onCommentBlock(commentBlock);
		return null;
	}

	@Override
	public Void onDivisionBlock(MarkdomDivisionBlock divisionBlock) {
		choice.onDivisionBlock(divisionBlock);
		return null;
	}

	@Override
	public Void onHeadingBlock(MarkdomHeadingBlock headingBlock) {
		choice.onHeadingBlock(headingBlock);
		return null;
	}

	@Override
	public Void onOrderedListBlock(MarkdomOrderedListBlock orderedListBlock) {
		choice.onOrderedListBlock(orderedListBlock);
		return null;
	}

	@Override
	public Void onParagraphBlock(MarkdomParagraphBlock paragraphBlock) {
		choice.onParagraphBlock(paragraphBlock);
		return null;
	}

	@Override
	public Void onQuoteBlock(MarkdomQuoteBlock quoteBlock) {
		choice.onQuoteBlock(quoteBlock);
		return null;
	}

	@Override
	public Void onUnorderedListBlock(MarkdomUnorderedListBlock unorderedListBlock) {
		choice.onUnorderedListBlock(unorderedListBlock);
		return null;
	}

}