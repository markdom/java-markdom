package io.markdom.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public final class Nodes implements Iterable<Node> {

	private final List<Node> nodes = new LinkedList<>();

	public Nodes add(Node node) {
		nodes.add(ObjectHelper.notNull("node", node));
		return this;
	}

	@Override
	public Iterator<Node> iterator() {
		return nodes.iterator();
	}

}