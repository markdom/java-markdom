package io.markdom.model.selection;

import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomListItem;
import io.markdom.model.MarkdomQuoteBlock;

public abstract class AbstractMarkdomBlockParentSelection<Result> implements MarkdomBlockParentSelection<Result> {

	@Override
	public Result onDocument(MarkdomDocument document) {
		return null;
	}

	@Override
	public Result onListItem(MarkdomListItem listItem) {
		return null;
	}

	@Override
	public Result onQuoteBlock(MarkdomQuoteBlock quoteBlock) {
		return null;
	}

}
