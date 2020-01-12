package io.markdom.model.selection;

import io.markdom.model.MarkdomBlock;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomListItem;

public interface MarkdomNodeSelection<Result> {

	public Result onDocument(MarkdomDocument document);

	public Result onBlock(MarkdomBlock block);

	public Result onListItem(MarkdomListItem listItem);

	public Result onContent(MarkdomContent content);

}
