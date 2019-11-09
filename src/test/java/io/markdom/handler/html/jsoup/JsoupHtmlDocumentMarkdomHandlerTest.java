package io.markdom.handler.html.jsoup;

import static org.hamcrest.MatcherAssert.assertThat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.xmlunit.matchers.CompareMatcher;

import io.markdom.TestHelper;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class JsoupHtmlDocumentMarkdomHandlerTest {

	@Test
	@SneakyThrows
	public void handleExampleObject() {

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDocument document = TestHelper.getExampleDocument(factory);

		Document htmlDocument = document.handle(new JsoupHtmlDocumentMarkdomHandler()).asDocument();
		htmlDocument.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
		String html = htmlDocument.outerHtml();

		Document exampleDocument = Jsoup.parse(TestHelper.readExampleHtml());
		exampleDocument.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

		assertThat(html, CompareMatcher.isIdenticalTo(exampleDocument.html()).ignoreWhitespace());

	}

}
