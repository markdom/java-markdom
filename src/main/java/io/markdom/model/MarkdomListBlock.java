package io.markdom.model;

import java.util.Collections;
import java.util.List;

import io.markdom.common.MarkdomListBlockType;

public interface MarkdomListBlock extends MarkdomBlock {

	public MarkdomListBlockType getListBlockType();

	public List<? extends MarkdomListItem> getListItems();

	public MarkdomListBlock addListItem(MarkdomListItem listItem);

	public MarkdomListBlock addListItems(MarkdomListItem... listItems);

	public MarkdomListBlock addListItems(Iterable<MarkdomListItem> listItems);

	@Override
	public default List<MarkdomListItem> getChildren() {
		return Collections.unmodifiableList(getListItems());
	}

}
