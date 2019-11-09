package io.markdom.model.selection;

import io.markdom.model.MarkdomBlock;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomListItem;
import io.markdom.model.choice.MarkdomNodeChoice;

public final class MarkdomNodeChoiceSelection extends AbstractMarkdomNodeSelection<Void> {

	private final MarkdomNodeChoice choice;

	public MarkdomNodeChoiceSelection(MarkdomNodeChoice choice) {
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
	public Void onBlock(MarkdomBlock block) {
		choice.onBlock(block);
		return null;
	}

	@Override
	public Void onListItem(MarkdomListItem listItem) {
		choice.onListItem(listItem);
		return null;
	}

	@Override
	public Void onContent(MarkdomContent content) {
		choice.onContent(content);
		return null;
	}

}