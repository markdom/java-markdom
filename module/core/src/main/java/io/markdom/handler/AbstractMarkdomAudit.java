package io.markdom.handler;

import java.util.Optional;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;

public abstract class AbstractMarkdomAudit implements MarkdomAudit {

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
	}

	@Override
	public void onCommentBlock() {
	}

	@Override
	public void onDivisionBlock() {
	}

	@Override
	public void onHeadingBlock(MarkdomHeadingLevel level) {
	}

	@Override
	public void onOrderedListBlock(Integer startIndex) {
	}

	@Override
	public void onParagraphBlock() {
	}

	@Override
	public void onQuoteBlock() {
	}

	@Override
	public void onUnorderedListBlock() {
	}

	@Override
	public void onCodeContent(String code) {
	}

	@Override
	public void onEmphasisContent(MarkdomEmphasisLevel level) {
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
	}

	@Override
	public void onLinkContent(String uri, Optional<String> title) {
	}

	@Override
	public void onTextContent(String text) {
	}

}
