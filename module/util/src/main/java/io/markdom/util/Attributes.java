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

	public Attributes add(Optional<Attribute> attribute) {
		ObjectHelper.notNull("attribute", attribute).map(this::add);
		return this;
	}

	@Override
	public Iterator<Attribute> iterator() {
		return attributes.iterator();
	}

}