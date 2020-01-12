package io.markdom.model.choice;

import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomListItem;
import io.markdom.model.MarkdomQuoteBlock;

public abstract class AbstractMarkdomBlockParentChoice implements MarkdomBlockParentChoice {

	@Override
	public void onDocument(MarkdomDocument document) {
	}

	@Override
	public void onListItem(MarkdomListItem listItem) {
	}

	@Override
	public void onQuoteBlock(MarkdomQuoteBlock quoteBlock) {
	}

}
