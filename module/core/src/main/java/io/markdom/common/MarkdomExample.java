package io.markdom.common;

import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MarkdomExample {

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
