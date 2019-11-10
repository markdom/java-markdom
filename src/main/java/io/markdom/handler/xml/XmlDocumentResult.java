package io.markdom.handler.xml;

public interface XmlDocumentResult<Document, Element> {

	public Document asDocument();

	public default String asDocumentText() {
		return asDocumentText(false);
	}

	public String asDocumentText(boolean pretty);

	public Element asElement();

	public default String asElementText() {
		return asElementText(false);
	}

	public String asElementText(boolean pretty);

}
