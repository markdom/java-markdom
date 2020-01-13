package io.markdom.handler.audit.nodetype;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.handler.AbstractMarkdomAudit;
import io.markdom.util.ObjectHelper;

public final class NodeTypeMarkdomAudit extends AbstractMarkdomAudit {

	private final NodeTypeMarkdomAuditHandler handler;

	public NodeTypeMarkdomAudit(NodeTypeMarkdomAuditHandler handler) {
		this.handler = ObjectHelper.notNull("handler", handler);
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
		handler.onBlockType(MarkdomBlockType.CODE);
	}

	@Override
	public void onCommentBlock() {
		handler.onBlockType(MarkdomBlockType.COMMENT);
	}

	@Override
	public void onDivisionBlock() {
		handler.onBlockType(MarkdomBlockType.DIVISION);
	}

	@Override
	public void onHeadingBlock(MarkdomHeadingLevel level) {
		handler.onBlockType(MarkdomBlockType.HEADING);
	}

	@Override
	public void onOrderedListBlock(Integer startIndex) {
		handler.onBlockType(MarkdomBlockType.ORDERED_LIST);
	}

	@Override
	public void onParagraphBlock() {
		handler.onBlockType(MarkdomBlockType.PARAGRAPH);
	}

	@Override
	public void onQuoteBlock() {
		handler.onBlockType(MarkdomBlockType.QUOTE);
	}

	@Override
	public void onUnorderedListBlock() {
		handler.onBlockType(MarkdomBlockType.UNORDERED_LIST);
	}

	@Override
	public void onCodeContent(String code) {
		handler.onContentType(MarkdomContentType.CODE);
	}

	@Override
	public void onEmphasisContent(MarkdomEmphasisLevel level) {
		handler.onContentType(MarkdomContentType.EMPHASIS);
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		handler.onContentType(MarkdomContentType.IMAGE);
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		handler.onContentType(MarkdomContentType.LINE_BREAK);
	}

	@Override
	public void onLinkContent(String uri, Optional<String> title) {
		handler.onContentType(MarkdomContentType.LINK);
	}

	@Override
	public void onTextContent(String text) {
		handler.onContentType(MarkdomContentType.TEXT);
	}

}
