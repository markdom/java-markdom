package io.markdom.handler.html.w3c;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import io.markdom.handler.html.AbstractHtmlDocumentMarkdomHandler;
import io.markdom.handler.html.DefaultHtmlDelegate;
import io.markdom.handler.html.HtmlDelegate;
import io.markdom.util.Attribute;

public final class XhtmlDocumentMarkdomHandler extends AbstractHtmlDocumentMarkdomHandler<XhtmlDocumentResult> {

	private static final DefaultHtmlDelegate DEFAULT_DELEGATE = new DefaultHtmlDelegate();

	private static final String DEFAULT_TITLE = "Markdom";

	private static final String XMLNS_NAMESPACE = "http://www.w3.org/1999/xhtml";

	private static final String LEGACY_SYSTEM_ID = "about:legacy-compat";

	private final DocumentBuilder builder;

	private Document document;

	private Element element;

	public XhtmlDocumentMarkdomHandler(DocumentBuilder builder) {
		this(DEFAULT_DELEGATE, DEFAULT_TITLE, builder);
	}

	public XhtmlDocumentMarkdomHandler(DocumentBuilder builder, String title) {
		this(DEFAULT_DELEGATE, title, builder);
	}

	public XhtmlDocumentMarkdomHandler(HtmlDelegate delegate, DocumentBuilder builder) {
		this(delegate, DEFAULT_TITLE, builder);
	}

	public XhtmlDocumentMarkdomHandler(HtmlDelegate delegate, String title, DocumentBuilder builder) {
		super(delegate, title);
		if (null == builder) {
			throw new IllegalArgumentException("The given document builder is null");
		}
		this.builder = builder;
	}

	@Override
	protected final void beginDocument(String dtdQualifiedName, String rootTagName) {
		DOMImplementation domImplementation = builder.getDOMImplementation();
		DocumentType doctype = domImplementation.createDocumentType(dtdQualifiedName, "", LEGACY_SYSTEM_ID);
		document = domImplementation.createDocument(XMLNS_NAMESPACE, rootTagName, doctype);
		element = document.getDocumentElement();
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
	public XhtmlDocumentResult getResult() {
		return new XhtmlDocumentResult(builder, document);
	}

}
