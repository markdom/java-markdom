package io.markdom.handler;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.ObjectHelper;

public final class TypeMarkdomFilter implements MarkdomFilter {

	private final TypeMarkdomFilterHandler handler;

	public TypeMarkdomFilter(TypeMarkdomFilterHandler handler) {
		this.handler = ObjectHelper.notNull("handler", handler);
	}

	@Override
	public boolean testCodeBlock(String code, Optional<String> hint) {
		return handler.testBlockType(MarkdomBlockType.CODE);
	}

	@Override
	public boolean testCommentBlock() {
		return handler.testBlockType(MarkdomBlockType.COMMENT);
	}

	@Override
	public boolean testDivisionBlock() {
		return handler.testBlockType(MarkdomBlockType.DIVISION);
	}

	@Override
	public boolean testHeadingBlock(MarkdomHeadingLevel level) {
		return handler.testBlockType(MarkdomBlockType.HEADING);
	}

	@Override
	public boolean testOrderedListBlock(Integer startIndex) {
		return handler.testBlockType(MarkdomBlockType.ORDERED_LIST);
	}

	@Override
	public boolean testParagraphBlock() {
		return handler.testBlockType(MarkdomBlockType.PARAGRAPH);
	}

	@Override
	public boolean testQuoteBlock() {
		return handler.testBlockType(MarkdomBlockType.QUOTE);
	}

	@Override
	public boolean testUnorderedListBlock() {
		return handler.testBlockType(MarkdomBlockType.UNORDERED_LIST);
	}

	@Override
	public boolean testCodeContent(String code) {
		return handler.testContentType(MarkdomContentType.CODE);
	}

	@Override
	public boolean testEmphasisContent(MarkdomEmphasisLevel level) {
		return handler.testContentType(MarkdomContentType.EMPHASIS);
	}

	@Override
	public boolean testImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		return handler.testContentType(MarkdomContentType.IMAGE);
	}

	@Override
	public boolean testLineBreakContent(Boolean hard) {
		return handler.testContentType(MarkdomContentType.LINE_BREAK);
	}

	@Override
	public boolean testLinkContent(String uri, Optional<String> title) {
		return handler.testContentType(MarkdomContentType.LINK);
	}

	@Override
	public boolean testTextContent(String text) {
		return handler.testContentType(MarkdomContentType.TEXT);
	}

}
