package io.markdom.handler;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;

public interface MarkdomHandler<Result> {

	public void onDocumentBegin();

	public void onBlocksBegin();

	public void onBlockBegin(MarkdomBlockType type);

	public void onCodeBlock(String code, Optional<String> hint);

	public void onCommentBlock(String comment);

	public void onDivisionBlock();

	public void onHeadingBlockBegin(MarkdomHeadingLevel level);

	public void onHeadingBlockEnd(MarkdomHeadingLevel level);

	public void onOrderedListBlockBegin(Integer startIndex);

	public void onOrderedListBlockEnd(Integer startIndex);

	public void onParagraphBlockBegin();

	public void onParagraphBlockEnd();

	public void onQuoteBlockBegin();

	public void onQuoteBlockEnd();

	public void onUnorderedListBlockBegin();

	public void onUnorderedListBlockEnd();

	public void onBlockEnd(MarkdomBlockType type);

	public void onNextBlock();

	public void onBlocksEnd();

	public void onListItemsBegin();

	public void onListItemBegin();

	public void onListItemEnd();

	public void onNextListItem();

	public void onListItemsEnd();

	public void onContentsBegin();

	public void onContentBegin(MarkdomContentType type);

	public void onCodeContent(String code);

	public void onEmphasisContentBegin(MarkdomEmphasisLevel level);

	public void onEmphasisContentEnd(MarkdomEmphasisLevel level);

	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative);

	public void onLineBreakContent(Boolean hard);

	public void onLinkContentBegin(String uri, Optional<String> title);

	public void onLinkContentEnd(String uri, Optional<String> title);

	public void onTextContent(String text);

	public void onContentEnd(MarkdomContentType type);

	public void onNextContent();

	public void onContentsEnd();

	public void onDocumentEnd();

	public Result getResult();

}
