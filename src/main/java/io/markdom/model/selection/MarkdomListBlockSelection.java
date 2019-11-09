package io.markdom.model.selection;

import io.markdom.model.MarkdomOrderedListBlock;
import io.markdom.model.MarkdomUnorderedListBlock;

public interface MarkdomListBlockSelection<Result> {

	public Result onOrderedListBlock(MarkdomOrderedListBlock orderedListBlock);

	public Result onUnorderedListBlock(MarkdomUnorderedListBlock unorderedListBlock);

}
