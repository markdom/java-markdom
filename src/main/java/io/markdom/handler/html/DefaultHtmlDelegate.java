package io.markdom.handler.html;

import java.util.Optional;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.Attributes;
import io.markdom.util.Elements;

public class DefaultHtmlDelegate implements HtmlDelegate {

	@Override
	public Elements onCodeBlock(String code, Optional<String> hint) {
		return new Elements().add("pre").add("code");
	}

	@Override
	public Elements onDivisionBlock() {
		return new Elements().add("hr");
	}

	@Override
	public Elements onHeadingBlock(MarkdomHeadingLevel level) {
		return new Elements().add("h" + (level.ordinal() + 1));
	}

	@Override
	public Elements onOrderdListBlock(Integer startIndex) {
		return new Elements().add("ol", new Attributes().add("start", Integer.toString(startIndex)));
	}

	@Override
	public Elements onParagraphBlock() {
		return new Elements().add("p");
	}

	@Override
	public Elements onQuoteBlock() {
		return new Elements().add("blockquote");
	}

	@Override
	public Elements onUnorderedListBlock() {
		return new Elements().add("ul");
	}

	@Override
	public Elements onListItem() {
		return new Elements().add("li");
	}

	@Override
	public Elements onCodeContent(String code) {
		return new Elements().add("code");
	}

	@Override
	public Elements onEmphasisContent(MarkdomEmphasisLevel level) {
		switch (level) {
		case LEVEL_1:
			return new Elements().add("em");
		case LEVEL_2:
			return new Elements().add("strong");
		}
		throw new InternalError("Unexpected emphasis level: " + level);
	}

	@Override
	public Elements onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		Attributes attributes = new Attributes().add("src", uri).add("title", title).add("alt", alternative);
		return new Elements().add("img", attributes);
	}

	@Override
	public Elements onLineBreakContent() {
		return new Elements().add("br");
	}

	@Override
	public Elements onLinkContent(String uri, Optional<String> title) {
		Attributes attributes = new Attributes().add("href", uri).add("title", title);
		return new Elements().add("a", attributes);
	}

	@Override
	public Elements onTextContent(String text) {
		return new Elements();
	}

}
