package io.markdom.model.basic;

import java.util.List;
import java.util.Objects;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomListItem;
import io.markdom.model.MarkdomUnorderedListBlock;

public final class BasicMarkdomUnorderedListBlock extends AbstractMarkdomBlock implements MarkdomUnorderedListBlock {

	private final BasicMarkdomListBlockDelegate delegate = new BasicMarkdomListBlockDelegate(this);

	public BasicMarkdomUnorderedListBlock(MarkdomFactory factory) {
		super(factory);
	}

	@Override
	public List<? extends MarkdomListItem> getListItems() {
		return delegate.getListItems();
	}

	@Override
	public MarkdomUnorderedListBlock addListItem(MarkdomListItem listItem) {
		delegate.addListItem(listItem);
		return this;
	}

	@Override
	public MarkdomUnorderedListBlock addListItems(MarkdomListItem... listItems) {
		delegate.addListItems(listItems);
		return this;
	}

	@Override
	public MarkdomUnorderedListBlock addListItems(Iterable<MarkdomListItem> listItems) {
		delegate.addListItems(listItems);
		return this;
	}

	@Override
	protected void doHandle(MarkdomHandler<?> handler) {
		handler.onUnorderedListBlockBegin();
		delegate.onHandle(handler);
		handler.onUnorderedListBlockEnd();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getListItems());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (null == object) {
			return false;
		} else if (!(object instanceof MarkdomUnorderedListBlock)) {
			return false;
		}
		MarkdomUnorderedListBlock other = (MarkdomUnorderedListBlock) object;
		if (!getListItems().equals(other.getListItems())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [listItems=" + getListItems() + "]";
	}

}
