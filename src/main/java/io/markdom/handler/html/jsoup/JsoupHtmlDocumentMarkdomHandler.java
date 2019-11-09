package io.markdom.handler.html.jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;

import io.markdom.common.Attribute;
import io.markdom.handler.html.AbstractHtmlDocumentMarkdomHandler;
import io.markdom.handler.html.DefaultHtmlDelegate;
import io.markdom.handler.html.HtmlDelegate;

public final class JsoupHtmlDocumentMarkdomHandler extends AbstractHtmlDocumentMarkdomHandler<Document> {

	private static final DefaultHtmlDelegate DEFAULT_DELEGATE = new DefaultHtmlDelegate();

	private static final String DEFAULT_TITLE = "Markdom";

	private static final String DTD_QUALIFIED_NAME = "html";

	private Document document;

	private Element element;

	public JsoupHtmlDocumentMarkdomHandler() {
		this(DEFAULT_DELEGATE, DEFAULT_TITLE);
	}

	public JsoupHtmlDocumentMarkdomHandler(String title) {
		this(DEFAULT_DELEGATE, title);
	}

	public JsoupHtmlDocumentMarkdomHandler(HtmlDelegate delegate) {
		this(delegate, DEFAULT_TITLE);
	}

	public JsoupHtmlDocumentMarkdomHandler(HtmlDelegate delegate, String title) {
		super(delegate, title);
	}

	protected final Document getDocument() {
		return document;
	}

	@Override
	protected final void beginDocument(String rootTagName) {
		document = new Document("");
		document.appendChild(new DocumentType(DTD_QUALIFIED_NAME, "", ""));
		element = document;
		pushElement(rootTagName);
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
			element.attr(attribute.getKey(), attribute.getValue());
		}
	}

	@Override
	protected final void setText(String text) {
		element.text(text);
	}

	@Override
	protected void setCharacterData(String text) {
		element.text(text);
	}

	@Override
	protected final void popElement() {
		element = (Element) element.parent();
	}

	@Override
	protected void endDocument() {
		popElement();
	}

	@Override
	public Document getResult() {
		return document;
	}

}
