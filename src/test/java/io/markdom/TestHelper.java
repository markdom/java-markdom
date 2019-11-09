package io.markdom;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.markenwerk.utils.text.fetcher.BufferedTextFetcher;

@UtilityClass
public class TestHelper {

	public static Reader openExampleHtml() {
		return open("example.html");
	}

	public static Reader openExampleXhtml() {
		return open("example.xhtml");
	}

	public static Reader openExampleJson() {
		return open("example.json");
	}

	public static Reader openExampleXml() {
		return open("example.xml");
	}

	private static Reader open(String filename) {
		InputStream in = TestHelper.class.getResourceAsStream("/" + filename);
		return new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
	}

	public static String readExampleHtml() {
		return read(openExampleHtml());
	}

	public static String readExampleXhtml() {
		return read(openExampleXhtml());
	}

	public static String readExampleJson() {
		return read(openExampleJson());
	}

	public static String readExampleXml() {
		return read(openExampleXml());
	}

	@SneakyThrows
	private static String read(Reader reader) {
		return new BufferedTextFetcher().read(reader, true);
	}

	public static MarkdomDocument getExampleDocument(MarkdomFactory factory) {
		// @formatter:off
		return factory.document(
			factory.headingBlock(
				MarkdomHeadingLevel.LEVEL_1,
				factory.textContent("Markdom")
			), 
			factory.orderedListBlock(
				1,
				factory.listItem(
					factory.paragraphBlock(
						factory.linkContent(
							"#Bar",
							factory.textContent("Foo")
						)
					)
				),
				factory.listItem(
					factory.paragraphBlock(
						factory.textContent("Lorem ipsum"),
						factory.lineBreakContent(true),
						factory.codeContent("dolor sit amet")
					) 
				),
				factory.listItem(
					factory.quoteBlock(
						factory.paragraphBlock(
							factory.emphasisContent(
								MarkdomEmphasisLevel.LEVEL_1,
								factory.textContent("Baz")
							)
						)
					)
				)
			),
			factory.codeBlock("goto 11")
		);
		// @formatter:on
	}

	@SneakyThrows
	public String toString(Document document) {

		DocumentType doctype = document.getDoctype();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new StringWriter());

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

		if (null != doctype) {
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
		}

		transformer.transform(source, result);

		return result.getWriter().toString();
	}

}
