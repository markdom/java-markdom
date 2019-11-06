package io.markdom.handler.html.w3c;

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

public class XhtmlDocumentMarkdomHandlerTest {

	@Test
	@SneakyThrows
	public void handleExampleObject() {

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDocument document = TestHelper.getExampleDocument(factory);

		DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
		xmlFactory.setNamespaceAware(true);

		DocumentBuilder xmlBuilder = xmlFactory.newDocumentBuilder();
		Document xmlDocument = document.handle(new XhtmlDocumentMarkdomHandler(xmlBuilder));
		String html = TestHelper.toString(xmlDocument);

		assertThat(html, CompareMatcher.isIdenticalTo(TestHelper.readExampleXhtml()).ignoreWhitespace());

	}

}
