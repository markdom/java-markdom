package io.markdom.model.selection;

import io.markdom.model.MarkdomOrderedListBlock;
import io.markdom.model.MarkdomUnorderedListBlock;
import io.markdom.model.choice.MarkdomListBlockChoice;
import io.markdom.util.ObjectHelper;

public final class MarkdomListBlockChoiceSelection extends AbstractMarkdomListBlockSelection<Void> {

	private final MarkdomListBlockChoice choice;

	public MarkdomListBlockChoiceSelection(MarkdomListBlockChoice choice) {
		this.choice = ObjectHelper.notNull("choice", choice);
	}

	@Override
	public Void onOrderedListBlock(MarkdomOrderedListBlock orderedListBlock) {
		choice.onOrderedListBlock(orderedListBlock);
		return null;
	}

	@Override
	public Void onUnorderedListBlock(MarkdomUnorderedListBlock unorderedListBlock) {
		choice.onUnorderedListBlock(unorderedListBlock);
		return null;
	}

}