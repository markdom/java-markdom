package io.markdom.handler;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.ObjectHelper;

public final class TypebasedMarkdomAudit implements MarkdomAudit {

	private final TypebasedMarkdomAuditDelegate delegate;

	public TypebasedMarkdomAudit(TypebasedMarkdomAuditDelegate delegate) {
		this.delegate = ObjectHelper.notNull("delegate", delegate);
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
		delegate.onBlockType(MarkdomBlockType.CODE);
	}

	@Override
	public void onCommentBlock() {
		delegate.onBlockType(MarkdomBlockType.COMMENT);
	}

	@Override
	public void onDivisionBlock() {
		delegate.onBlockType(MarkdomBlockType.DIVISION);
	}

	@Override
	public void onHeadingBlock(MarkdomHeadingLevel level) {
		delegate.onBlockType(MarkdomBlockType.HEADING);
	}

	@Override
	public void onOrderedListBlock(Integer startIndex) {
		delegate.onBlockType(MarkdomBlockType.ORDERED_LIST);
	}

	@Override
	public void onParagraphBlock() {
		delegate.onBlockType(MarkdomBlockType.PARAGRAPH);
	}

	@Override
	public void onQuoteBlock() {
		delegate.onBlockType(MarkdomBlockType.QUOTE);
	}

	@Override
	public void onUnorderedListBlock() {
		delegate.onBlockType(MarkdomBlockType.UNORDERED_LIST);
	}

	@Override
	public void onCodeContent(String code) {
		delegate.onContentType(MarkdomContentType.CODE);
	}

	@Override
	public void onEmphasisContent(MarkdomEmphasisLevel level) {
		delegate.onContentType(MarkdomContentType.EMPHASIS);
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		delegate.onContentType(MarkdomContentType.IMAGE);
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		delegate.onContentType(MarkdomContentType.LINE_BREAK);
	}

	@Override
	public void onLinkContent(String uri, Optional<String> title) {
		delegate.onContentType(MarkdomContentType.LINK);
	}

	@Override
	public void onTextContent(String text) {
		delegate.onContentType(MarkdomContentType.TEXT);
	}

}
