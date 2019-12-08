package io.markdom.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import lombok.Data;

@Data
public final class Attributes implements Iterable<Attribute> {

	private final List<Attribute> attributes = new LinkedList<>();

	public Attributes add(Attribute attribute) {
		attributes.add(ObjectHelper.notNull("attribute", attribute));
		return this;
	}

	public Attributes add(String key, Optional<String> value) {
		if (ObjectHelper.notNull("optional value", value).isPresent()) {
			attributes.add(new Attribute(key, value.get()));
		}
		return this;
	}

	public Attributes add(String key, String value) {
		attributes.add(new Attribute(key, value));
		return this;
	}

	@Override
	public Iterator<Attribute> iterator() {
		return attributes.iterator();
	}

}