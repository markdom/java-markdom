package io.markdom.model.choice;

import io.markdom.model.MarkdomBlock;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomListItem;

public interface MarkdomNodeChoice {

	public void onDocument(MarkdomDocument document);

	public void onBlock(MarkdomBlock block);

	public void onListItem(MarkdomListItem listItem);

	public void onContent(MarkdomContent content);

}
