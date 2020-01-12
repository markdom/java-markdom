package io.markdom.model;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomListBlockType;
import io.markdom.model.selection.MarkdomBlockSelection;
import io.markdom.model.selection.MarkdomListBlockSelection;

public interface MarkdomOrderedListBlock extends MarkdomListBlock {

	@Override
	public default MarkdomBlockType getBlockType() {
		return MarkdomBlockType.ORDERED_LIST;
	}

	@Override
	public default MarkdomListBlockType getListBlockType() {
		return MarkdomListBlockType.ORDERED;
	}

	public Integer getStartIndex();

	public MarkdomOrderedListBlock setStartIndex(Integer startIndex);

	@Override
	public MarkdomOrderedListBlock addListItem(MarkdomListItem listItem);

	@Override
	public MarkdomOrderedListBlock addListItems(MarkdomListItem... listItems);

	@Override
	public MarkdomOrderedListBlock addListItems(Iterable<MarkdomListItem> listItems);

	@Override
	public default <Result> Result select(MarkdomBlockSelection<Result> selection) {
		return selection.onOrderedListBlock(this);
	}

	@Override
	public default <Result> Result select(MarkdomListBlockSelection<Result> selection) {
		return selection.onOrderedListBlock(this);
	}

}
