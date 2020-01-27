package io.markdom.util;

import java.util.Optional;

import lombok.Data;

@Data
public final class Element implements Node {

	private final String tagName;

	private final Attributes attributes;

	private final Nodes nodes;

	public Element(String tagName) {
		this(tagName, new Attributes(), new Nodes());
	}

	public Element(String tagName, Attributes attributes, Nodes nodes) {
		this.tagName = ObjectHelper.notNull("tag name", tagName);
		this.attributes = ObjectHelper.notNull("attributes", attributes);
		this.nodes = ObjectHelper.notNull("nodes", nodes);
	}

	public Element add(Attribute attribute) {
		attributes.add(ObjectHelper.notNull("attribute", attribute));
		return this;
	}

	public Element add(Optional<Attribute> attribute) {
		ObjectHelper.notNull("attribute", attribute).map(this::add);
		return this;
	}

	public Element add(Node node) {
		nodes.add(ObjectHelper.notNull("node", node));
		return this;
	}

	@Override
	public <Result> Result select(NodeSelection<Result> selection) {
		return selection.select(this);
	}

}