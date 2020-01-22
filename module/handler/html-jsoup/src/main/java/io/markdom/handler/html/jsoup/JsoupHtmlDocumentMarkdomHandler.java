package io.markdom.handler.html.jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;

import io.markdom.handler.html.AbstractHtmlDocumentMarkdomHandler;
import io.markdom.handler.html.DefaultHtmlDelegate;
import io.markdom.handler.html.HtmlDelegate;
import io.markdom.util.Attribute;
import io.markdom.util.Attributes;

public final class JsoupHtmlDocumentMarkdomHandler extends AbstractHtmlDocumentMarkdomHandler<JsoupHtmlDocumentResult> {

	private static final DefaultHtmlDelegate DEFAULT_DELEGATE = new DefaultHtmlDelegate();

	private static final String DEFAULT_TITLE = "Markdom";

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

	@Override
	protected final void beginDocument(String dtdQualifiedName, String rootTagName) {
		document = new Document("");
		document.appendChild(new DocumentType(dtdQualifiedName, "", ""));
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
	protected final void setAttributes(Attributes attributes) {
		for (Attribute attribute : attributes) {
			element.attr(attribute.getKey(), attribute.getValue());
		}
	}

	@Override
	protected final void setText(String text) {
		element.appendText(text);
	}

	@Override
	protected void setCharacterData(String text) {
		element.appendText(text);
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
	public JsoupHtmlDocumentResult getResult() {
		return new JsoupHtmlDocumentResult(document);
	}

}
