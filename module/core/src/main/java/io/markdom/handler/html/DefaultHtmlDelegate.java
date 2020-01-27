package io.markdom.handler.html;

import java.util.Optional;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.Attribute;
import io.markdom.util.CharacterData;
import io.markdom.util.Element;
import io.markdom.util.Gap;
import io.markdom.util.Nodes;
import io.markdom.util.Text;

public class DefaultHtmlDelegate implements HtmlDelegate {

	@Override
	public Nodes onDocument(Gap gap) {
		return new Nodes().add(gap);
	}

	@Override
	public Nodes onCodeBlock(String code, Optional<String> hint) {
		return new Nodes().add(new Element("pre").add(new Element("code").add(new CharacterData(code))));
	}

	@Override
	public Nodes onDivisionBlock() {
		return new Nodes().add(new Element("hr"));
	}

	@Override
	public Nodes onHeadingBlock(MarkdomHeadingLevel level, Gap gap) {
		return new Nodes().add(new Element("h" + (level.ordinal() + 1)).add(gap));
	}

	@Override
	public Nodes onOrderdListBlock(Integer startIndex, Gap gap) {
		return new Nodes().add(new Element("ol").add(new Attribute("start", Integer.toString(startIndex))).add(gap));
	}

	@Override
	public Nodes onParagraphBlock(Gap gap) {
		return new Nodes().add(new Element("p").add(gap));
	}

	@Override
	public Nodes onQuoteBlock(Gap gap) {
		return new Nodes().add(new Element("blockquote").add(gap));
	}

	@Override
	public Nodes onUnorderedListBlock(Gap gap) {
		return new Nodes().add(new Element("ul").add(gap));
	}

	@Override
	public Nodes onListItem(Gap gap) {
		return new Nodes().add(new Element("li").add(gap));
	}

	@Override
	public Nodes onCodeContent(String code) {
		return new Nodes().add(new Element("code").add(new Text(code)));
	}

	@Override
	public Nodes onEmphasisContent(MarkdomEmphasisLevel level, Gap gap) {
		switch (level) {
			case LEVEL_1:
				return new Nodes().add(new Element("em").add(gap));
			case LEVEL_2:
				return new Nodes().add(new Element("strong").add(gap));
		}
		throw new InternalError("Unexpected emphasis level: " + level);
	}

	@Override
	public Nodes onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		return new Nodes().add(new Element("img").add(new Attribute("src", uri)).add(map("title", title)).add(map("alt", alternative)));
	}

	@Override
	public Nodes onLineBreakContent() {
		return new Nodes().add(new Element("br"));
	}

	@Override
	public Nodes onLinkContent(String uri, Optional<String> title, Gap gap) {
		return new Nodes().add(new Element("a").add(new Attribute("href", uri)).add(map("title", title)).add(gap));
	}

	@Override
	public Nodes onTextContent(String text) {
		return new Nodes().add(new Text(text));
	}

	private Optional<Attribute> map(String key, Optional<String> value) {
		return value.map(actualValue -> new Attribute(key, actualValue));
	}

}
