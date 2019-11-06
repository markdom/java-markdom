package io.markdom.common;

import java.util.Collections;

import lombok.Data;

@Data
public final class Element {

	public Element(String tagName) {
		this(tagName, Collections.emptyList());
	}

	public Element(String tagName, Iterable<Attribute> attributes) {
		this.tagName = tagName;
		this.attributes = attributes;
	}

	private final String tagName;

	private final Iterable<Attribute> attributes;

}