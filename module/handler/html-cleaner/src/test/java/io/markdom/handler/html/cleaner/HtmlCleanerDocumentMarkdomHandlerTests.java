package io.markdom.handler.html.cleaner;

import static org.hamcrest.MatcherAssert.assertThat;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.Serializer;
import org.htmlcleaner.TagNode;
import org.junit.jupiter.api.Test;
import org.xmlunit.matchers.CompareMatcher;

import io.markdom.TestHelper;
import io.markdom.common.MarkdomExample;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class HtmlCleanerDocumentMarkdomHandlerTests {

	@Test
	@SneakyThrows
	public void handleExampleObject() {

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDocument document = MarkdomExample.getExampleDocument(factory);

		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties properties = cleaner.getProperties();
		Serializer serializer = new PrettyXmlSerializer(properties);

		TagNode htmlDocument = document.handle(new HtmlCleanerHtmlDocumentMarkdomHandler()).asDocument();
		String html = serializer.getAsString(htmlDocument);

		TagNode exampleDocument = cleaner.clean(TestHelper.openExampleHtml());

		assertThat(html, CompareMatcher.isIdenticalTo(serializer.getAsString(exampleDocument)).ignoreWhitespace());

	}

}
