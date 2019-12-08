package io.markdom.model.selection;

import io.markdom.model.MarkdomBlock;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomListItem;

public abstract class AbstractMarkdomNodeSelection<Result> implements MarkdomNodeSelection<Result> {

	@Override
	public Result onDocument(MarkdomDocument document) {
		return null;
	}

	@Override
	public Result onBlock(MarkdomBlock block) {
		return null;
	}

	@Override
	public Result onListItem(MarkdomListItem listItem) {
		return null;
	}

	@Override
	public Result onContent(MarkdomContent content) {
		return null;
	}

}
