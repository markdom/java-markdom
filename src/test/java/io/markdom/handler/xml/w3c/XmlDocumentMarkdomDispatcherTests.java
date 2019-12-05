package io.markdom.handler.xml.w3c;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import io.markdom.TestHelper;
import io.markdom.handler.MarkdomDispatcher;
import io.markdom.handler.MarkdomDocumentMarkdomHandler;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class XmlDocumentMarkdomDispatcherTests {

	@Test
	@SneakyThrows
	public void dispatchExampleDocument() {

		DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
		xmlFactory.setNamespaceAware(true);

		DocumentBuilder xmlBuilder = xmlFactory.newDocumentBuilder();
		Document xmlDocument = xmlBuilder.parse(new InputSource(TestHelper.openExampleXml()));

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDispatcher dispatcher = new XmlDocumentMarkdomDispatcher(xmlDocument);
		MarkdomHandler<MarkdomDocument> handler = new MarkdomDocumentMarkdomHandler(factory);

		MarkdomDocument document = dispatcher.handle(handler);

		assertEquals(TestHelper.getExampleDocument(factory), document);

	}

}
