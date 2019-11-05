package io.markdom;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import lombok.SneakyThrows;
import net.markenwerk.utils.text.fetcher.BufferedTextFetcher;

public class TestHelper {

	public static Reader openExampleJson() {
		InputStream in = TestHelper.class.getResourceAsStream("/example.json");
		Reader reader = new InputStreamReader(in, Charset.forName("UTF-8"));
		return new BufferedReader(reader);
	}

	@SneakyThrows
	public static String readExampleJson() {
		return new BufferedTextFetcher().read(openExampleJson(), true);
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

}
