package io.markdom.handler;

import java.util.Optional;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.common.MarkdomNodeKind;
import io.markdom.util.ObjectHelper;

public final class NodeKindMarkdomFilter extends AbstractMarkdomFilter {

	private final NodeKindMarkdomFilterHandler handler;

	public NodeKindMarkdomFilter(NodeKindMarkdomFilterHandler handler) {
		this.handler = ObjectHelper.notNull("handler", handler);
	}

	@Override
	public boolean testCodeBlock(String code, Optional<String> hint) {
		return handler.testNodeKind(MarkdomNodeKind.CODE_BLOCK);
	}

	@Override
	public boolean testCommentBlock() {
		return handler.testNodeKind(MarkdomNodeKind.COMMENT_BLOCK);
	}

	@Override
	public boolean testDivisionBlock() {
		return handler.testNodeKind(MarkdomNodeKind.DIVISION_BLOCK);
	}

	@Override
	public boolean testHeadingBlock(MarkdomHeadingLevel level) {
		return handler.testNodeKind(MarkdomNodeKind.HEADING_BLOCK);
	}

	@Override
	public boolean testOrderedListBlock(Integer startIndex) {
		return handler.testNodeKind(MarkdomNodeKind.ORDERED_LIST_BLOCK);
	}

	@Override
	public boolean testParagraphBlock() {
		return handler.testNodeKind(MarkdomNodeKind.PARAGRAPH_BLOCK);
	}

	@Override
	public boolean testQuoteBlock() {
		return handler.testNodeKind(MarkdomNodeKind.QUOTE_BLOCK);
	}

	@Override
	public boolean testUnorderedListBlock() {
		return handler.testNodeKind(MarkdomNodeKind.UNORDERED_LIST_BLOCK);
	}

	@Override
	public boolean testCodeContent(String code) {
		return handler.testNodeKind(MarkdomNodeKind.CODE_CONTENT);
	}

	@Override
	public boolean testEmphasisContent(MarkdomEmphasisLevel level) {
		return handler.testNodeKind(MarkdomNodeKind.EMPHASIS_CONTENT);
	}

	@Override
	public boolean testImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		return handler.testNodeKind(MarkdomNodeKind.IMAGE_CONTENT);
	}

	@Override
	public boolean testLineBreakContent(Boolean hard) {
		return handler.testNodeKind(MarkdomNodeKind.LINE_BREAK_CONTENT);
	}

	@Override
	public boolean testLinkContent(String uri, Optional<String> title) {
		return handler.testNodeKind(MarkdomNodeKind.LINK_CONTENT);
	}

	@Override
	public boolean testTextContent(String text) {
		return handler.testNodeKind(MarkdomNodeKind.TEXT_CONTENT);
	}

}
