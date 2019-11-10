package io.markdom.model.basic;

import java.util.Iterator;
import java.util.List;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.ManagedMarkdomListItem;
import io.markdom.model.MarkdomListBlock;
import io.markdom.model.MarkdomListItem;
import io.markdom.util.ObjectHelper;

public final class BasicMarkdomListBlockDelegate {

	private final List<ManagedMarkdomListItem> listItems;

	BasicMarkdomListBlockDelegate(MarkdomListBlock parent) {
		this.listItems = new BasicMarkdomListItemList(parent);
	}

	public List<? extends MarkdomListItem> getListItems() {
		return listItems;
	}

	public void addListItem(MarkdomListItem listItem) {
		listItems.add((ManagedMarkdomListItem) listItem);
	}

	public void addListItems(MarkdomListItem... listItems) {
		for (MarkdomListItem listItem : ObjectHelper.notNull("array of list items", listItems)) {
			addListItem(listItem);
		}
	}

	public void addListItems(Iterable<MarkdomListItem> listItems) {
		for (MarkdomListItem listItem : ObjectHelper.notNull("iterable of list items", listItems)) {
			addListItem(listItem);
		}
	}

	public void onHandle(MarkdomHandler<?> handler) {
		handler.onListItemsBegin();
		Iterator<ManagedMarkdomListItem> iterator = listItems.iterator();
		if (iterator.hasNext()) {
			iterator.next().onHandle(handler);
			while (iterator.hasNext()) {
				handler.onNextListItem();
				iterator.next().onHandle(handler);
			}
		}
		handler.onListItemsEnd();
	}

	@Override
	public String toString() {
		return listItems.toString();
	}

}
