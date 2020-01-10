package io.markdom.handler;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.ObjectHelper;

public final class AuditingMarkdomHandler<Result> implements MarkdomHandler<Result> {

	private final MarkdomHandler<Result> handler;

	private final MarkdomAudit audit;

	public AuditingMarkdomHandler(MarkdomHandler<Result> handler, MarkdomAudit audit) {
		this.handler = ObjectHelper.notNull("handler", handler);
		this.audit = ObjectHelper.notNull("audit", audit);
	}

	@Override
	public void onDocumentBegin() {
		handler.onDocumentBegin();
	}

	@Override
	public void onBlocksBegin() {
		handler.onBlocksBegin();
	}

	@Override
	public void onBlockBegin(MarkdomBlockType type) {
		handler.onBlockBegin(type);
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
		audit.onCodeBlock(code, hint);
		handler.onCodeBlock(code, hint);
	}

	@Override
	public void onCommentBlock(String comment) {
		audit.onCommentBlock();
		handler.onCommentBlock(comment);
	}

	@Override
	public void onDivisionBlock() {
		audit.onDivisionBlock();
		handler.onDivisionBlock();
	}

	@Override
	public void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		audit.onHeadingBlock(level);
		handler.onHeadingBlockBegin(level);
	}

	@Override
	public void onHeadingBlockEnd(MarkdomHeadingLevel level) {
		handler.onHeadingBlockEnd(level);
	}

	@Override
	public void onOrderedListBlockBegin(Integer startIndex) {
		audit.onOrderedListBlock(startIndex);
		handler.onOrderedListBlockBegin(startIndex);
	}

	@Override
	public void onOrderedListBlockEnd(Integer startIndex) {
		handler.onOrderedListBlockEnd(startIndex);
	}

	@Override
	public void onParagraphBlockBegin() {
		audit.onParagraphBlock();
		handler.onParagraphBlockBegin();
	}

	@Override
	public void onParagraphBlockEnd() {
		handler.onParagraphBlockEnd();
	}

	@Override
	public void onQuoteBlockBegin() {
		audit.onQuoteBlock();
		handler.onQuoteBlockBegin();
	}

	@Override
	public void onQuoteBlockEnd() {
		handler.onQuoteBlockEnd();
	}

	@Override
	public void onUnorderedListBlockBegin() {
		audit.onUnorderedListBlock();
		handler.onUnorderedListBlockBegin();
	}

	@Override
	public void onUnorderedListBlockEnd() {
		handler.onUnorderedListBlockEnd();
	}

	@Override
	public void onBlockEnd(MarkdomBlockType type) {
		handler.onBlockEnd(type);
	}

	@Override
	public void onNextBlock() {
		handler.onNextBlock();
	}

	@Override
	public void onBlocksEnd() {
		handler.onBlocksEnd();
	}

	@Override
	public void onListItemsBegin() {
		handler.onListItemsBegin();
	}

	@Override
	public void onListItemBegin() {
		handler.onListItemBegin();
	}

	@Override
	public void onListItemEnd() {
		handler.onListItemEnd();
	}

	@Override
	public void onNextListItem() {
		handler.onNextListItem();
	}

	@Override
	public void onListItemsEnd() {
		handler.onListItemsEnd();
	}

	@Override
	public void onContentsBegin() {
		handler.onContentsBegin();
	}

	@Override
	public void onContentBegin(MarkdomContentType type) {
		handler.onContentBegin(type);
	}

	@Override
	public void onCodeContent(String code) {
		audit.onCodeContent(code);
		handler.onCodeContent(code);
	}

	@Override
	public void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		audit.onEmphasisContent(level);
		handler.onEmphasisContentBegin(level);
	}

	@Override
	public void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
		handler.onEmphasisContentEnd(level);
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		audit.onImageContent(uri, title, alternative);
		handler.onImageContent(uri, title, alternative);
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		audit.onLineBreakContent(hard);
		handler.onLineBreakContent(hard);
	}

	@Override
	public void onLinkContentBegin(String uri, Optional<String> title) {
		audit.onLinkContent(uri, title);
		handler.onLinkContentBegin(uri, title);
	}

	@Override
	public void onLinkContentEnd(String uri, Optional<String> title) {
		handler.onLinkContentEnd(uri, title);
	}

	@Override
	public void onTextContent(String text) {
		audit.onTextContent(text);
		handler.onTextContent(text);
	}

	@Override
	public void onContentEnd(MarkdomContentType type) {
		handler.onContentEnd(type);
	}

	@Override
	public void onNextContent() {
		handler.onNextContent();
	}

	@Override
	public void onContentsEnd() {
		handler.onContentsEnd();
	}

	@Override
	public void onDocumentEnd() {
		handler.onDocumentEnd();
	}

	@Override
	public Result getResult() {
		return handler.getResult();
	}

}
