package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.markdom.common.MarkdomKeys;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomTextContent;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Property;

public final class BasicMarkdomTextContent extends AbstractMarkdomContent implements MarkdomTextContent {

	// @formatter:off
	private static final List<Property<MarkdomTextContent, ?>> PROPERTIES = new ArrayList<>(Arrays.asList(
		new Property<>(MarkdomKeys.TEXT, MarkdomTextContent::getText)
	));
	// @formatter:on	

	private String text;

	public BasicMarkdomTextContent(MarkdomFactory factory, String text) {
		super(factory);
		setText(text);
	}

	@Override
	public String getText() {
		return text;
	}

	public BasicMarkdomTextContent setText(String text) {
		this.text = ObjectHelper.notNull("text", text);
		return this;
	}

	@Override
	public void doHandle(MarkdomHandler<?> handler) {
		handler.onTextContent(text);
	}

	@Override
	public int hashCode() {
		return ObjectHelper.hashCode(this, PROPERTIES);
	}

	@Override
	public boolean equals(Object object) {
		return ObjectHelper.equals(this, MarkdomTextContent.class, PROPERTIES, object);
	}

	@Override
	public String toString() {
		return ObjectHelper.toString(this, PROPERTIES);
	}

}
