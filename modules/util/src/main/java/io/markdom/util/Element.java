package io.markdom.util;

import java.util.Iterator;

import lombok.Data;

@Data
public final class Element implements Iterable<Attribute> {

	private final String tagName;

	private final Attributes  attributes;

	public Element(String tagName) {
		this(tagName, new Attributes());
	}

	public Element(String tagName, Attributes attributes) {
		this.tagName = ObjectHelper.notNull("tag name", tagName);
		this.attributes = ObjectHelper.notNull("attributes", attributes);
	}

	@Override
	public Iterator<Attribute> iterator() {
		return attributes.iterator();
	}

}