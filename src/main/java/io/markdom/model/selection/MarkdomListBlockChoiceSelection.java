package io.markdom.model.selection;

import io.markdom.model.MarkdomOrderedListBlock;
import io.markdom.model.MarkdomUnorderedListBlock;
import io.markdom.model.choice.MarkdomListBlockChoice;

public final class MarkdomListBlockChoiceSelection extends AbstractMarkdomListBlockSelection<Void> {

	private final MarkdomListBlockChoice choice;

	public MarkdomListBlockChoiceSelection(MarkdomListBlockChoice choice) {
		if (null == choice) {
			throw new IllegalArgumentException("The given choice is null");
		}
		this.choice = choice;
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