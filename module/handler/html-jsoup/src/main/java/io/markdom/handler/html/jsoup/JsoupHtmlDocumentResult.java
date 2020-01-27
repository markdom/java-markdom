package io.markdom.handler.html.jsoup;

import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import io.markdom.handler.html.HtmlDocumentResult;
import io.markdom.util.Attribute;
import io.markdom.util.Attributes;
import io.markdom.util.ObjectHelper;

public final class JsoupHtmlDocumentResult implements HtmlDocumentResult<Document, Element, List<Node>> {

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
		for (Node blockNode : asNodes()) {
			element.appendChild(blockNode.clone());
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
	public List<Node> asNodes() {
		return asDocument().body().childNodes();
	}

	@Override
	public String asNodesText(boolean pretty) {
		StringBuilder builder = new StringBuilder();
		Iterator<Node> iterator = asNodes().iterator();
		if (iterator.hasNext()) {
			append(builder, iterator.next(), pretty);
			while (iterator.hasNext()) {
				if (pretty) {
					builder.append("\n");
				}
				append(builder, iterator.next(), pretty);
			}
		}
		return builder.toString();
	}

	private void append(StringBuilder builder, Node blockNode, boolean pretty) {
		blockNode.ownerDocument().outputSettings().prettyPrint(pretty);
		builder.append(blockNode.outerHtml());
	}

}
