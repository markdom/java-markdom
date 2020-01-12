package io.markdom.handler;

import java.util.Optional;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.common.MarkdomNodeKind;
import io.markdom.util.ObjectHelper;

public final class NodeKindMarkdomAudit extends AbstractMarkdomAudit {

	private final NodeKindMarkdomAuditHandler handler;

	public NodeKindMarkdomAudit(NodeKindMarkdomAuditHandler handler) {
		this.handler = ObjectHelper.notNull("handler", handler);
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
		handler.onNodeKind(MarkdomNodeKind.CODE_BLOCK);
	}

	@Override
	public void onCommentBlock() {
		handler.onNodeKind(MarkdomNodeKind.COMMENT_BLOCK);
	}

	@Override
	public void onDivisionBlock() {
		handler.onNodeKind(MarkdomNodeKind.DIVISION_BLOCK);
	}

	@Override
	public void onHeadingBlock(MarkdomHeadingLevel level) {
		handler.onNodeKind(MarkdomNodeKind.HEADING_BLOCK);
	}

	@Override
	public void onOrderedListBlock(Integer startIndex) {
		handler.onNodeKind(MarkdomNodeKind.ORDERED_LIST_BLOCK);
	}

	@Override
	public void onParagraphBlock() {
		handler.onNodeKind(MarkdomNodeKind.PARAGRAPH_BLOCK);
	}

	@Override
	public void onQuoteBlock() {
		handler.onNodeKind(MarkdomNodeKind.QUOTE_BLOCK);
	}

	@Override
	public void onUnorderedListBlock() {
		handler.onNodeKind(MarkdomNodeKind.UNORDERED_LIST_BLOCK);
	}

	@Override
	public void onCodeContent(String code) {
		handler.onNodeKind(MarkdomNodeKind.CODE_CONTENT);
	}

	@Override
	public void onEmphasisContent(MarkdomEmphasisLevel level) {
		handler.onNodeKind(MarkdomNodeKind.EMPHASIS_CONTENT);
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		handler.onNodeKind(MarkdomNodeKind.IMAGE_CONTENT);
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		handler.onNodeKind(MarkdomNodeKind.LINE_BREAK_CONTENT);
	}

	@Override
	public void onLinkContent(String uri, Optional<String> title) {
		handler.onNodeKind(MarkdomNodeKind.LINK_CONTENT);
	}

	@Override
	public void onTextContent(String text) {
		handler.onNodeKind(MarkdomNodeKind.TEXT_CONTENT);
	}

}
