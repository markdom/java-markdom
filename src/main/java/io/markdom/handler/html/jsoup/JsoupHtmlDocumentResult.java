package io.markdom.handler.html.jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.markdom.handler.html.HtmlDocumentResult;
import io.markdom.util.Attribute;
import io.markdom.util.Attributes;
import io.markdom.util.ObjectHelper;

public final class JsoupHtmlDocumentResult implements HtmlDocumentResult<Document, Element, Elements> {

	private final Document document;

	public JsoupHtmlDocumentResult(Document document) {
		this.document = ObjectHelper.notNull("document", document);
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
	public Element asElement(String tagName, Attributes attributes) {
		Document document = new Document(this.document.baseUri());
		Element element = document.createElement(tagName);
		document.appendChild(element);
		for (Attribute attribute : attributes) {
			element.attr(attribute.getKey(), attribute.getValue());
		}
		for (Element blockElement : asElements()) {
			element.appendChild(blockElement.clone());
		}
		return element;
	}

	@Override
	public String asElementText(String tagName, Attributes attributes, boolean pretty) {
		Element element = asElement(tagName, attributes);
		element.ownerDocument().outputSettings().prettyPrint(pretty);
		return element.outerHtml();
	}

	@Override
	public Elements asElements() {
		return asDocument().body().children();
	}

	@Override
	public String asElementsText(boolean pretty) {
		StringBuilder builder = new StringBuilder();
		for (Element blockElement : asElements()) {
			blockElement.ownerDocument().outputSettings().prettyPrint(pretty);
			builder.append(blockElement.outerHtml());
		}
		return builder.toString();
	}

}
