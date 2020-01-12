package io.markdom.handler.xml.w3c;

import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import io.markdom.util.ObjectHelper;
import io.markdom.util.XmlHelper;

public final class XmlDocumentResult implements io.markdom.handler.xml.XmlDocumentResult<Document, Element> {

	private final Document document;

	public XmlDocumentResult(Document document) {
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
	public Element asElement() {
		return asDocument().getDocumentElement();
	}

	@Override
	public String asElementText(boolean pretty) {
		return XmlHelper.asText(asElement(), pretty);
	}

}
