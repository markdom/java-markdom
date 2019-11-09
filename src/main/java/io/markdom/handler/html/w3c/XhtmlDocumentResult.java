package io.markdom.handler.html.w3c;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.markdom.common.Attribute;
import io.markdom.handler.html.HtmlDocumentResult;
import lombok.SneakyThrows;
import net.markenwerk.commons.iterables.NodeListIterable;

public class XhtmlDocumentResult implements HtmlDocumentResult<Document, Element, NodeList> {

	private final DocumentBuilder builder;

	private final Document document;

	public XhtmlDocumentResult(DocumentBuilder builder, Document document) {
		if (null == builder) {
			throw new IllegalArgumentException("The given document builder is null");
		}
		if (null == document) {
			throw new IllegalArgumentException("The given document is null");
		}
		this.builder = builder;
		this.document = document;
	}

	@Override
	public Document asDocument() {
		return (Document) this.document.cloneNode(true);
	}

	@Override
	public String asDocumentText(boolean pretty) {
		return transform(asDocument(), pretty);
	}

	@Override
	public Element asElement(String tagName, Iterable<Attribute> attributes) {
		Document document = builder.newDocument();
		Element element = document.createElement(tagName);
		for (Attribute attribute : attributes) {
			element.setAttribute(attribute.getKey(), attribute.getValue());
		}
		for (Node node : new NodeListIterable(asBlockElements())) {
			element.appendChild(document.importNode(node, true));
		}
		return element;
	}

	@Override
	public String asElementText(String tagName, Iterable<Attribute> attributes, boolean pretty) {
		return transform(asElement(tagName, attributes), pretty);
	}

	@Override
	public NodeList asBlockElements() {
		return ((Element) asDocument().getLastChild().getChildNodes()).getLastChild().getChildNodes();
	}

	@Override
	public String asBlockElementsText(boolean pretty) {
		StringBuilder builder = new StringBuilder();
		for (Node node : new NodeListIterable(asBlockElements())) {
			builder.append(transform(node, pretty));
		}
		return builder.toString();
	}

	@SneakyThrows
	private String transform(Node node, boolean pretty) {

		StreamResult result = new StreamResult(new StringWriter());

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

		if (pretty) {
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		}

		if (node instanceof Document) {
			DocumentType doctype = ((Document) node).getDoctype();
			if (null != doctype) {
				transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
				transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
			}
		} else {
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		}

		transformer.transform(new DOMSource(node), result);
		return result.getWriter().toString();

	}

}
