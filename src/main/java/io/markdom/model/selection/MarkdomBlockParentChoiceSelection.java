package io.markdom.model.selection;

import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomListItem;
import io.markdom.model.MarkdomQuoteBlock;
import io.markdom.model.choice.MarkdomBlockParentChoice;

public final class MarkdomBlockParentChoiceSelection extends AbstractMarkdomBlockParentSelection<Void> {

	private final MarkdomBlockParentChoice choice;

	public MarkdomBlockParentChoiceSelection(MarkdomBlockParentChoice choice) {
		if (null == choice) {
			throw new IllegalArgumentException("The given choice is null");
		}
		this.choice = choice;
	}

	@Override
	public Void onDocument(MarkdomDocument document) {
		choice.onDocument(document);
		return null;
	}

	@Override
	public Void onListItem(MarkdomListItem listItem) {
		choice.onListItem(listItem);
		return null;
	}

	@Override
	public Void onQuoteBlock(MarkdomQuoteBlock quoteBlock) {
		choice.onQuoteBlock(quoteBlock);
		return null;
	}

}