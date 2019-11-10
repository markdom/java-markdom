package io.markdom.handler.xml.w3c;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import io.markdom.handler.xml.AbstractXmlDocumentMarkdomHandler;
import io.markdom.util.Attribute;
import io.markdom.util.ObjectHelper;

public final class XmlDocumentMarkdomHandler extends AbstractXmlDocumentMarkdomHandler<XmlDocumentResult> {

	private final DocumentBuilder builder;

	private Document document;

	private Element element;

	public XmlDocumentMarkdomHandler(DocumentBuilder builder) {
		this.builder = ObjectHelper.notNull("builder", builder);
	}

	protected final Document getDocument() {
		return document;
	}

	@Override
	protected final void beginDocument(String dtdQualifiedName, String dtdPublicId, String dtdSystemId, String rootTagName,
		String documentVersion, String xmlnsNameSpace) {
		DOMImplementation dom = builder.getDOMImplementation();
		if (null != dtdQualifiedName) {
			DocumentType doctype = dom.createDocumentType(dtdQualifiedName, dtdPublicId, dtdSystemId);
			document = builder.getDOMImplementation().createDocument(xmlnsNameSpace, rootTagName, doctype);
		} else {
			document = builder.getDOMImplementation().createDocument(xmlnsNameSpace, rootTagName, null);
		}
		element = document.getDocumentElement();
		element.setAttribute("version", documentVersion);
	}

	@Override
	protected final void pushElement(String tagName) {
		Element element = document.createElement(tagName);
		this.element.appendChild(element);
		this.element = element;
	}

	@Override
	protected final void setAttributes(Iterable<Attribute> attributes) {
		for (Attribute attribute : attributes) {
			element.setAttribute(attribute.getKey(), attribute.getValue());
		}
	}

	@Override
	protected final void setText(String text) {
		Node textNode = document.createTextNode(text);
		element.appendChild(textNode);
	}

	@Override
	protected final void setCharacterData(String text) {
		Node characterNode = document.createCDATASection(text);
		element.appendChild(characterNode);
	}

	@Override
	protected final void popElement() {
		element = (Element) element.getParentNode();
	}

	@Override
	protected void endDocument() {
	}

	@Override
	public XmlDocumentResult getResult() {
		return new XmlDocumentResult(document);
	}

}
