package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.markdom.common.MarkdomKeys;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomListItem;
import io.markdom.model.MarkdomOrderedListBlock;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Property;

public final class BasicMarkdomOrderedListBlock extends AbstractMarkdomBlock implements MarkdomOrderedListBlock {

	// @formatter:off
	private static final List<Property<MarkdomOrderedListBlock, ?>> PROPERTIES = new ArrayList<>(Arrays.asList(
		new Property<>(MarkdomKeys.START_INDEX, MarkdomOrderedListBlock::getStartIndex),
		new Property<>(MarkdomKeys.ITEMS, MarkdomOrderedListBlock::getListItems)
	));
	// @formatter:on	

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
		this.startIndex = ObjectHelper.notNull("start index", startIndex);
		return this;
	}

	@Override
	public List<MarkdomListItem> getListItems() {
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
		return ObjectHelper.hashCode(this, PROPERTIES);
	}

	@Override
	public boolean equals(Object object) {
		return ObjectHelper.equals(this, MarkdomOrderedListBlock.class, PROPERTIES, object);
	}

	@Override
	public String toString() {
		return ObjectHelper.toString(this, PROPERTIES);
	}

}
