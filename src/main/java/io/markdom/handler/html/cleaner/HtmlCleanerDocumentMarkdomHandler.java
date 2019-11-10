package io.markdom.handler.html.cleaner;

import org.htmlcleaner.ContentNode;
import org.htmlcleaner.DoctypeToken;
import org.htmlcleaner.TagNode;

import io.markdom.handler.html.AbstractHtmlDocumentMarkdomHandler;
import io.markdom.handler.html.DefaultHtmlDelegate;
import io.markdom.handler.html.HtmlDelegate;
import io.markdom.util.Attribute;

public final class HtmlCleanerDocumentMarkdomHandler extends AbstractHtmlDocumentMarkdomHandler<HtmlCleanerDocumentResult> {

	private static final DefaultHtmlDelegate DEFAULT_DELEGATE = new DefaultHtmlDelegate();

	private static final String DEFAULT_TITLE = "Markdom";

	private TagNode document;

	private TagNode element;

	public HtmlCleanerDocumentMarkdomHandler() {
		this(DEFAULT_DELEGATE, DEFAULT_TITLE);
	}

	public HtmlCleanerDocumentMarkdomHandler(String title) {
		this(DEFAULT_DELEGATE, title);
	}

	public HtmlCleanerDocumentMarkdomHandler(HtmlDelegate delegate) {
		this(delegate, DEFAULT_TITLE);
	}

	public HtmlCleanerDocumentMarkdomHandler(HtmlDelegate delegate, String title) {
		super(delegate, title);
	}

	@Override
	protected final void beginDocument(String dtdQualifiedName, String rootTagName) {
		document = new TagNode(rootTagName);
		document.setDocType(new DoctypeToken(dtdQualifiedName, null, null, null));
		element = document;
	}

	@Override
	protected final void pushElement(String tagName) {
		TagNode element = new TagNode(tagName);
		this.element.addChild(element);
		this.element = element;

	}

	@Override
	protected final void setAttributes(Iterable<Attribute> attributes) {
		for (Attribute attribute : attributes) {
			element.addAttribute(attribute.getKey(), attribute.getValue());
		}
	}

	@Override
	protected final void setText(String text) {
		element.addChild(new ContentNode(text));
	}

	@Override
	protected void setCharacterData(String text) {
		element.addChild(new ContentNode(text));
	}

	@Override
	protected final void popElement() {
		element = (TagNode) element.getParent();
	}

	@Override
	protected void endDocument() {
	}

	@Override
	public HtmlCleanerDocumentResult getResult() {
		return new HtmlCleanerDocumentResult(document);
	}

}
