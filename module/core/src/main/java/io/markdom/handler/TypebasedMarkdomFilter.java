package io.markdom.handler;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.ObjectHelper;

public final class TypebasedMarkdomFilter implements MarkdomFilter {

	private final TypebasedMarkdomFilterDelegate delegate;

	public TypebasedMarkdomFilter(TypebasedMarkdomFilterDelegate delegate) {
		this.delegate = ObjectHelper.notNull("delegate", delegate);
	}

	@Override
	public boolean testCodeBlock(String code, Optional<String> hint) {
		return delegate.testBlockType(MarkdomBlockType.CODE);
	}

	@Override
	public boolean testCommentBlock() {
		return delegate.testBlockType(MarkdomBlockType.COMMENT);
	}

	@Override
	public boolean testDivisionBlock() {
		return delegate.testBlockType(MarkdomBlockType.DIVISION);
	}

	@Override
	public boolean testHeadingBlock(MarkdomHeadingLevel level) {
		return delegate.testBlockType(MarkdomBlockType.HEADING);
	}

	@Override
	public boolean testOrderedListBlock(Integer startIndex) {
		return delegate.testBlockType(MarkdomBlockType.ORDERED_LIST);
	}

	@Override
	public boolean testParagraphBlock() {
		return delegate.testBlockType(MarkdomBlockType.PARAGRAPH);
	}

	@Override
	public boolean testQuoteBlock() {
		return delegate.testBlockType(MarkdomBlockType.QUOTE);
	}

	@Override
	public boolean testUnorderedListBlock() {
		return delegate.testBlockType(MarkdomBlockType.UNORDERED_LIST);
	}

	@Override
	public boolean testCodeContent(String code) {
		return delegate.testContentType(MarkdomContentType.CODE);
	}

	@Override
	public boolean testEmphasisContent(MarkdomEmphasisLevel level) {
		return delegate.testContentType(MarkdomContentType.EMPHASIS);
	}

	@Override
	public boolean testImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		return delegate.testContentType(MarkdomContentType.IMAGE);
	}

	@Override
	public boolean testLineBreakContent(Boolean hard) {
		return delegate.testContentType(MarkdomContentType.LINE_BREAK);
	}

	@Override
	public boolean testLinkContent(String uri, Optional<String> title) {
		return delegate.testContentType(MarkdomContentType.LINK);
	}

	@Override
	public boolean testTextContent(String text) {
		return delegate.testContentType(MarkdomContentType.TEXT);
	}

}
