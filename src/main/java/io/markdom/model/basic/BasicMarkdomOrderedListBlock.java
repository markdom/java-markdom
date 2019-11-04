package io.markdom.model.basic;

import java.util.List;
import java.util.Objects;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomListItem;
import io.markdom.model.MarkdomOrderedListBlock;

public final class BasicMarkdomOrderedListBlock extends AbstractMarkdomBlock implements MarkdomOrderedListBlock {

	private final BasicMarkdomListBlockDelegate delegate = new BasicMarkdomListBlockDelegate(this);

	private Integer startIndex;

	public BasicMarkdomOrderedListBlock(MarkdomFactory factory, Integer startIndex) {
		super(factory);
		setStartIndex(startIndex);
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public BasicMarkdomOrderedListBlock setStartIndex(Integer startIndex) {
		if (null == startIndex) {
			throw new IllegalArgumentException("The given start index is null");
		}
		this.startIndex = startIndex;
		return this;
	}

	@Override
	public List<? extends MarkdomListItem> getListItems() {
		return delegate.getListItems();
	}

	@Override
	public MarkdomOrderedListBlock addListItem(MarkdomListItem listItem) {
		delegate.addListItem(listItem);
		return this;
	}

	@Override
	public MarkdomOrderedListBlock addListItems(MarkdomListItem... listItems) {
		delegate.addListItems(listItems);
		return this;
	}

	@Override
	public MarkdomOrderedListBlock addListItems(Iterable<MarkdomListItem> listItems) {
		delegate.addListItems(listItems);
		return this;
	}

	@Override
	protected void doHandle(MarkdomHandler<?> handler) {
		handler.onOrderedListBlockBegin(startIndex);
		delegate.onHandle(handler);
		handler.onOrderedListBlockEnd(startIndex);
	}

	@Override
	public int hashCode() {
		return Objects.hash(startIndex, getListItems());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (null == object) {
			return false;
		} else if (!(object instanceof MarkdomOrderedListBlock)) {
			return false;
		}
		MarkdomOrderedListBlock other = (MarkdomOrderedListBlock) object;
		if (!Objects.equals(startIndex, other.getStartIndex())) {
			return false;
		} else if (!getListItems().equals(other.getListItems())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [listItems=" + getListItems() + "]";
	}

}
