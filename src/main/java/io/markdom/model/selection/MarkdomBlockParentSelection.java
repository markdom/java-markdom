package io.markdom.model.selection;

import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomListItem;
import io.markdom.model.MarkdomQuoteBlock;

public interface MarkdomBlockParentSelection<Result> {

	public Result onDocument(MarkdomDocument document);

	public Result onListItem(MarkdomListItem listItem);

	public Result onQuoteBlock(MarkdomQuoteBlock quoteBlock);

}
