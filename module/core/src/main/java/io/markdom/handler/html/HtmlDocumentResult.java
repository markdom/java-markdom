package io.markdom.handler.html;

import io.markdom.util.Attributes;

public interface HtmlDocumentResult<Document, Element, Nodes> {

	public Document asDocument();

	public default String asDocumentText() {
		return asDocumentText(false);
	}

	public String asDocumentText(boolean pretty);

	public default Element asElement(String tagName) {
		return asElement(tagName, new Attributes());
	}

	public Element asElement(String tagName, Attributes attributes);

	public default String asElementText(String tagName) {
		return asElementText(tagName, new Attributes(), false);
	}

	public default String asElementText(String tagName, boolean pretty) {
		return asElementText(tagName, new Attributes(), pretty);
	}

	public default String asElementText(String tagName, Attributes attributes) {
		return asElementText(tagName, attributes, false);
	}

	public String asElementText(String tagName, Attributes attributes, boolean pretty);

	public Nodes asNodes();

	public default String asNodesText() {
		return asNodesText(false);
	}

	public String asNodesText(boolean pretty);

}
