package io.markdom.model;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomListBlockType;
import io.markdom.model.selection.MarkdomBlockSelection;
import io.markdom.model.selection.MarkdomListBlockSelection;

public interface MarkdomUnorderedListBlock extends MarkdomListBlock {

	@Override
	public default MarkdomBlockType getBlockType() {
		return MarkdomBlockType.UNORDERED_LIST;
	}

	@Override
	public default MarkdomListBlockType getListBlockType() {
		return MarkdomListBlockType.UNORDERED;
	}

	@Override
	public MarkdomUnorderedListBlock addListItem(MarkdomListItem listItem);

	@Override
	public MarkdomUnorderedListBlock addListItems(MarkdomListItem... listItems);

	@Override
	public MarkdomUnorderedListBlock addListItems(Iterable<MarkdomListItem> listItems);

	@Override
	public default <Result> Result select(MarkdomBlockSelection<Result> selection) {
		return selection.onUnorderedListBlock(this);
	}

	@Override
	public default <Result> Result select(MarkdomListBlockSelection<Result> selection) {
		return selection.onUnorderedListBlock(this);
	}

}
