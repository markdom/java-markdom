package io.markdom.handler.html.jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.markdom.common.Attribute;
import io.markdom.handler.html.HtmlDocumentResult;

public final class JsoupHtmlDocumentResult implements HtmlDocumentResult<Document, Element, Elements> {

	private final Document document;

	public JsoupHtmlDocumentResult(Document document) {
		if (null == document) {
			throw new IllegalArgumentException("The given document is null");
		}
		this.document = document;
	}

	@Override
	public Document asDocument() {
		return document.clone();
	}

	@Override
	public String asDocumentText(boolean pretty) {
		document.outputSettings().prettyPrint(pretty);
		return asDocument().outerHtml();
	}

	@Override
	public Element asElement(String tagName, Iterable<Attribute> attributes) {
		Document document = new Document(this.document.baseUri());
		Element element = document.createElement(tagName);
		document.appendChild(element);
		for (Attribute attribute : attributes) {
			element.attr(attribute.getKey(), attribute.getValue());
		}
		for (Element blockElement : asBlockElements()) {
			element.appendChild(blockElement.clone());
		}
		return element;
	}

	@Override
	public String asElementText(String tagName, Iterable<Attribute> attributes, boolean pretty) {
		Element element = asElement(tagName, attributes);
		element.ownerDocument().outputSettings().prettyPrint(pretty);
		return element.outerHtml();
	}

	@Override
	public Elements asBlockElements() {
		return asDocument().body().children();
	}

	@Override
	public String asBlockElementsText(boolean pretty) {
		document.outputSettings().prettyPrint(pretty);
		return asBlockElements().outerHtml();
	}

}
