package io.markdom.model.choice;

import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomListItem;
import io.markdom.model.MarkdomQuoteBlock;

public interface MarkdomBlockParentChoice {

	public void onDocument(MarkdomDocument document);

	public void onListItem(MarkdomListItem listItem);

	public void onQuoteBlock(MarkdomQuoteBlock quoteBlock);

}
