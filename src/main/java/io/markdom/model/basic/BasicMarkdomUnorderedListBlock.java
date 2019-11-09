package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.markdom.common.MarkdomKeys;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomListItem;
import io.markdom.model.MarkdomUnorderedListBlock;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Property;

public final class BasicMarkdomUnorderedListBlock extends AbstractMarkdomBlock implements MarkdomUnorderedListBlock {

	// @formatter:off
	private static final List<Property<MarkdomUnorderedListBlock, ?>> PROPERTIES = new ArrayList<>(Arrays.asList(
		new Property<>(MarkdomKeys.ITEMS, MarkdomUnorderedListBlock::getListItems)
	));
	// @formatter:on	

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
		return ObjectHelper.hashCode(this, PROPERTIES);
	}

	@Override
	public boolean equals(Object object) {
		return ObjectHelper.equals(this, MarkdomUnorderedListBlock.class, PROPERTIES, object);
	}

	@Override
	public String toString() {
		return ObjectHelper.toString(this, PROPERTIES);
	}

}
