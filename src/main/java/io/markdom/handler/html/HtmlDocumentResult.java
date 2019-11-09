package io.markdom.handler.html;

import java.util.Collections;

import io.markdom.common.Attribute;

public interface HtmlDocumentResult<Document, Element, Elements> {

	public Document asDocument();

	public default String asDocumentText() {
		return asDocumentText(false);
	}

	public String asDocumentText(boolean pretty);

	public default Element asElement(String tagName) {
		return asElement(tagName, Collections.emptyList());
	}

	public Element asElement(String tagName, Iterable<Attribute> attributes);

	public default String asElementText(String tagName) {
		return asElementText(tagName, Collections.emptyList(), false);
	}

	public default String asElementText(String tagName, boolean pretty) {
		return asElementText(tagName, Collections.emptyList(), pretty);
	}

	public default String asElementText(String tagName, Iterable<Attribute> attributes) {
		return asElementText(tagName, attributes, false);
	}

	public String asElementText(String tagName, Iterable<Attribute> attributes, boolean pretty);

	public Elements asBlockElements();

	public default String asBlockElementsText() {
		return asBlockElementsText(false);
	}

	public String asBlockElementsText(boolean pretty);

}
