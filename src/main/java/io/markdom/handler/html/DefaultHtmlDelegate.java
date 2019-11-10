package io.markdom.handler.html;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.Attribute;
import io.markdom.util.Element;

public class DefaultHtmlDelegate implements HtmlDelegate {

	@Override
	public Iterable<Element> onCodeBlock(String code, Optional<String> hint) {
		return Arrays.asList(new Element("pre"), new Element("code"));
	}

	@Override
	public Iterable<Element> onDivisionBlock() {
		return Arrays.asList(new Element("hr"));
	}

	@Override
	public Iterable<Element> onHeadingBlock(MarkdomHeadingLevel level) {
		return Arrays.asList(new Element("h" + (level.ordinal() + 1)));
	}

	@Override
	public Iterable<Element> onOrderdListBlock(Integer startIndex) {
		return Arrays.asList(new Element("ol", Arrays.asList(new Attribute("start", Integer.toString(startIndex)))));
	}

	@Override
	public Iterable<Element> onParagraphBlock() {
		return Arrays.asList(new Element("p"));
	}

	@Override
	public Iterable<Element> onQuoteBlock() {
		return Arrays.asList(new Element("blockquote"));
	}

	@Override
	public Iterable<Element> onUnorderedListBlock() {
		return Arrays.asList(new Element("ul"));
	}

	@Override
	public Iterable<Element> onListItem() {
		return Arrays.asList(new Element("li"));
	}

	@Override
	public Iterable<Element> onCodeContent(String code) {
		return Arrays.asList(new Element("code"));
	}

	@Override
	public Iterable<Element> onEmphasisContent(MarkdomEmphasisLevel level) {
		switch (level) {
		case LEVEL_1:
			return Arrays.asList(new Element("em"));
		case LEVEL_2:
			return Arrays.asList(new Element("strong"));
		}
		throw new InternalError("Unexpected emphasis level: " + level);
	}

	@Override
	public Iterable<Element> onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		List<Attribute> attributes = new LinkedList<>();
		attributes.add(new Attribute("src", uri));
		if (title.isPresent()) {
			attributes.add(new Attribute("title", title.get()));
		}
		if (alternative.isPresent()) {
			attributes.add(new Attribute("alt", alternative.get()));
		}
		return Arrays.asList(new Element("img", attributes));
	}

	@Override
	public Iterable<Element> onLineBreakContent() {
		return Arrays.asList(new Element("br"));
	}

	@Override
	public Iterable<Element> onLinkContent(String uri, Optional<String> title) {
		List<Attribute> attributes = new LinkedList<>();
		attributes.add(new Attribute("href", uri));
		if (title.isPresent()) {
			attributes.add(new Attribute("title", title.get()));
		}
		return Arrays.asList(new Element("a", attributes));
	}

	@Override
	public Iterable<Element> onTextContent(String text) {
		return Arrays.asList();
	}

}
