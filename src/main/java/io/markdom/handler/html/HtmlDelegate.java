package io.markdom.handler.html;

import java.util.Optional;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.Elements;

public interface HtmlDelegate {

	public Elements onCodeBlock(String code, Optional<String> hint);

	public Elements onDivisionBlock();

	public Elements onHeadingBlock(MarkdomHeadingLevel level);

	public Elements onOrderdListBlock(Integer startIndex);

	public Elements onParagraphBlock();

	public Elements onQuoteBlock();

	public Elements onUnorderedListBlock();

	public Elements onListItem();

	public Elements onCodeContent(String code);

	public Elements onEmphasisContent(MarkdomEmphasisLevel level);

	public Elements onImageContent(String uri, Optional<String> title, Optional<String> alternative);

	public Elements onLineBreakContent();

	public Elements onLinkContent(String uri, Optional<String> title);

	public Elements onTextContent(String text);

}