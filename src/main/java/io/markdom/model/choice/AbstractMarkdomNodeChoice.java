package io.markdom.model.choice;

import io.markdom.model.MarkdomBlock;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomListItem;

public abstract class AbstractMarkdomNodeChoice implements MarkdomNodeChoice {

	@Override
	public void onDocument(MarkdomDocument document) {
	}

	@Override
	public void onBlock(MarkdomBlock block) {
	}

	@Override
	public void onListItem(MarkdomListItem listItem) {
	}

	@Override
	public void onContent(MarkdomContent content) {
	}

}
