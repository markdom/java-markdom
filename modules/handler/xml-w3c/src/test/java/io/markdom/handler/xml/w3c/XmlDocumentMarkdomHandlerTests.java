package io.markdom.handler.xml.w3c;

import static org.hamcrest.MatcherAssert.assertThat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xmlunit.matchers.CompareMatcher;

import io.markdom.TestHelper;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class XmlDocumentMarkdomHandlerTests {

	@Test
	@SneakyThrows
	public void handleExampleObject() {

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDocument document = TestHelper.getExampleDocument(factory);

		DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
		xmlFactory.setNamespaceAware(true);

		DocumentBuilder xmlBuilder = xmlFactory.newDocumentBuilder();
		Document xmlDocument = document.handle(new XmlDocumentMarkdomHandler(xmlBuilder)).asDocument();
		String xml = TestHelper.toString(xmlDocument);

		assertThat(xml, CompareMatcher.isIdenticalTo(TestHelper.readExampleXml()).ignoreWhitespace());

	}

}
