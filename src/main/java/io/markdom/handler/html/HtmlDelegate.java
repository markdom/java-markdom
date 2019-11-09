package io.markdom.handler.html;

import java.util.Optional;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.Element;

public interface HtmlDelegate {

	public Iterable<Element> onCodeBlock(String code, Optional<String> hint);

	public Iterable<Element> onDivisionBlock();

	public Iterable<Element> onHeadingBlock(MarkdomHeadingLevel level);

	public Iterable<Element> onOrderdListBlock(Integer startIndex);

	public Iterable<Element> onParagraphBlock();

	public Iterable<Element> onQuoteBlock();

	public Iterable<Element> onUnorderedListBlock();

	public Iterable<Element> onListItem();

	public Iterable<Element> onCodeContent(String code);

	public Iterable<Element> onEmphasisContent(MarkdomEmphasisLevel level);

	public Iterable<Element> onImageContent(String uri, Optional<String> title, Optional<String> alternative);

	public Iterable<Element> onLinebreakContent();

	public Iterable<Element> onLinkContent(String uri, Optional<String> title);

	public Iterable<Element> onTextContent(String text);

}