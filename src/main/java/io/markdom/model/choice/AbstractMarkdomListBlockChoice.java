package io.markdom.model.choice;

import io.markdom.model.MarkdomOrderedListBlock;
import io.markdom.model.MarkdomUnorderedListBlock;

public abstract class AbstractMarkdomListBlockChoice implements MarkdomListBlockChoice {

	@Override
	public void onOrderedListBlock(MarkdomOrderedListBlock orderedListBlock) {
	}

	@Override
	public void onUnorderedListBlock(MarkdomUnorderedListBlock unorderedListBlock) {
	}

}
