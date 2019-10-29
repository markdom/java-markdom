package io.markdom.model;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomListBlockType;

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

}
