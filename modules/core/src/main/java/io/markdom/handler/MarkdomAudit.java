package io.markdom.handler;

import java.util.Optional;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;

public interface MarkdomAudit {

	public void onCodeBlock(String code, Optional<String> hint);

	public void onCommentBlock();

	public void onDivisionBlock();

	public void onHeadingBlock(MarkdomHeadingLevel level);

	public void onOrderedListBlock(Integer startIndex);

	public void onParagraphBlock();

	public void onQuoteBlock();

	public void onUnorderedListBlock();

	public void onCodeContent(String code);

	public void onEmphasisContent(MarkdomEmphasisLevel level);

	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative);

	public void onLineBreakContent(Boolean hard);

	public void onLinkContent(String uri, Optional<String> title);

	public void onTextContent(String text);

}
