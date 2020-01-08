package io.markdom.handler;

import java.util.Optional;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;

public abstract class AbstractMarkdomFilter implements MarkdomFilter {

	@Override
	public boolean testCodeBlock(String code, Optional<String> hint) {
		return false;
	}

	@Override
	public boolean testCommentBlock() {
		return false;
	}

	@Override
	public boolean testDivisionBlock() {
		return false;
	}

	@Override
	public boolean testHeadingBlock(MarkdomHeadingLevel level) {
		return false;
	}

	@Override
	public boolean testOrderedListBlock(Integer startIndex) {
		return false;
	}

	@Override
	public boolean testParagraphBlock() {
		return false;
	}

	@Override
	public boolean testQuoteBlock() {
		return false;
	}

	@Override
	public boolean testUnorderedListBlock() {
		return false;
	}

	@Override
	public boolean testCodeContent(String code) {
		return false;
	}

	@Override
	public boolean testEmphasisContent(MarkdomEmphasisLevel level) {
		return false;
	}

	@Override
	public boolean testImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		return false;
	}

	@Override
	public boolean testLineBreakContent(Boolean hard) {
		return false;
	}

	@Override
	public boolean testLinkContent(String uri, Optional<String> title) {
		return false;
	}

	@Override
	public boolean testTextContent(String text) {
		return false;
	}

}
