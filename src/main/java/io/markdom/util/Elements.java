package io.markdom.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public final class Elements implements Iterable<Element> {

	private final List<Element> elements = new LinkedList<>();

	public Elements add(Element element) {
		elements.add(ObjectHelper.notNull("element", element));
		return this;
	}

	public Elements add(String tagName, Attributes attributes) {
		elements.add(new Element(tagName, attributes));
		return null;
	}

	public Elements add(String tagName) {
		elements.add(new Element(tagName));
		return this;
	}

	@Override
	public Iterator<Element> iterator() {
		return elements.iterator();
	}

}