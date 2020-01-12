package io.markdom.model.choice;

import io.markdom.model.MarkdomOrderedListBlock;
import io.markdom.model.MarkdomUnorderedListBlock;

public interface MarkdomListBlockChoice {

	public void onOrderedListBlock(MarkdomOrderedListBlock orderedListBlock);

	public void onUnorderedListBlock(MarkdomUnorderedListBlock unorderedListBlock);

}
