package io.markdom.handler;

import java.util.Optional;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;

public interface MarkdomFilter {

	public boolean testCodeBlock(String code, Optional<String> hint);

	public boolean testCommentBlock();

	public boolean testDivisionBlock();

	public boolean testHeadingBlock(MarkdomHeadingLevel level);

	public boolean testOrderedListBlock(Integer startIndex);

	public boolean testParagraphBlock();

	public boolean testQuoteBlock();

	public boolean testUnorderedListBlock();

	public boolean testCodeContent(String code);

	public boolean testEmphasisContent(MarkdomEmphasisLevel level);

	public boolean testImageContent(String uri, Optional<String> title, Optional<String> alternative);

	public boolean testLineBreakContent(Boolean hard);

	public boolean testLinkContent(String uri, Optional<String> title);

	public boolean testTextContent(String text);

}
