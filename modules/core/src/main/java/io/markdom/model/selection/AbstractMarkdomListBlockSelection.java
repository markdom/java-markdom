package io.markdom.model.selection;

import io.markdom.model.MarkdomOrderedListBlock;
import io.markdom.model.MarkdomUnorderedListBlock;

public abstract class AbstractMarkdomListBlockSelection<Result> implements MarkdomListBlockSelection<Result> {

	@Override
	public Result onOrderedListBlock(MarkdomOrderedListBlock orderedListBlock) {
		return null;
	}

	@Override
	public Result onUnorderedListBlock(MarkdomUnorderedListBlock unorderedListBlock) {
		return null;
	}

}
