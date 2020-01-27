package io.markdom.handler.html;

import java.util.Optional;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.Gap;
import io.markdom.util.Nodes;

public interface HtmlDelegate {

	public Nodes onDocument(Gap gap);

	public Nodes onCodeBlock(String code, Optional<String> hint);

	public Nodes onDivisionBlock();

	public Nodes onHeadingBlock(MarkdomHeadingLevel level, Gap gap);

	public Nodes onOrderdListBlock(Integer startIndex, Gap gap);

	public Nodes onParagraphBlock(Gap gap);

	public Nodes onQuoteBlock(Gap gap);

	public Nodes onUnorderedListBlock(Gap gap);

	public Nodes onListItem(Gap gap);

	public Nodes onCodeContent(String code);

	public Nodes onEmphasisContent(MarkdomEmphasisLevel level, Gap gap);

	public Nodes onImageContent(String uri, Optional<String> title, Optional<String> alternative);

	public Nodes onLineBreakContent();

	public Nodes onLinkContent(String uri, Optional<String> title, Gap gap);

	public Nodes onTextContent(String text);

}