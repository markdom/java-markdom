package io.markdom.handler.html.w3c;

import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.markdom.handler.html.HtmlDocumentResult;
import io.markdom.util.Attribute;
import io.markdom.util.Attributes;
import io.markdom.util.ObjectHelper;
import io.markdom.util.XmlHelper;
import net.markenwerk.commons.iterables.NodeListIterable;

public final class XhtmlDocumentResult implements HtmlDocumentResult<Document, Element, NodeList> {

	private final DocumentBuilder builder;

	private final Document document;

	public XhtmlDocumentResult(DocumentBuilder builder, Document document) {
		this.builder = ObjectHelper.notNull("builder", builder);
		this.document = ObjectHelper.notNull("document", document);
	}

	@Override
	public Document asDocument() {
		return (Document) this.document.cloneNode(true);
	}

	@Override
	public String asDocumentText(boolean pretty) {
		if (pretty) {
			return XmlHelper.asText(asDocument(), true);
		} else {
			return XmlHelper.asText(asDocument(), false).replaceFirst(Pattern.quote("\n"), "");
		}
	}

	@Override
	public Element asElement(String tagName, Attributes attributes) {
		Document document = builder.newDocument();
		Element element = document.createElement(tagName);
		for (Attribute attribute : attributes) {
			element.setAttribute(attribute.getKey(), attribute.getValue());
		}
		for (Node node : new NodeListIterable(asElements())) {
			element.appendChild(document.importNode(node, true));
		}
		return element;
	}

	@Override
	public String asElementText(String tagName, Attributes attributes, boolean pretty) {
		return XmlHelper.asText(asElement(tagName, attributes), pretty);
	}

	@Override
	public NodeList asElements() {
		return ((Element) asDocument().getLastChild().getChildNodes()).getLastChild().getChildNodes();
	}

	@Override
	public String asElementsText(boolean pretty) {
		StringBuilder builder = new StringBuilder();
		for (Node node : new NodeListIterable(asElements())) {
			builder.append(XmlHelper.asText(node, pretty));
		}
		return builder.toString();
	}

}
