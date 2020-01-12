package io.markdom.handler;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.ObjectHelper;

public class SimpleMarkdomFilter implements MarkdomFilter {

	private final Set<MarkdomBlockType> blockTypes;

	private final Set<MarkdomContentType> contentTypes;

	public SimpleMarkdomFilter(Set<MarkdomBlockType> blockTypes, Set<MarkdomContentType> contentTypes) {
		this.blockTypes = EnumSet.copyOf(ObjectHelper.notNull("set of block types", blockTypes));
		this.contentTypes = EnumSet.copyOf(ObjectHelper.notNull("set of content types", contentTypes));
	}

	@Override
	public boolean testCodeBlock(String code, Optional<String> hint) {
		return blockTypes.contains(MarkdomBlockType.CODE);
	}

	@Override
	public boolean testCommentBlock() {
		return blockTypes.contains(MarkdomBlockType.COMMENT);
	}

	@Override
	public boolean testDivisionBlock() {
		return blockTypes.contains(MarkdomBlockType.DIVISION);
	}

	@Override
	public boolean testHeadingBlock(MarkdomHeadingLevel level) {
		return blockTypes.contains(MarkdomBlockType.HEADING);
	}

	@Override
	public boolean testOrderedListBlock(Integer startIndex) {
		return blockTypes.contains(MarkdomBlockType.ORDERED_LIST);
	}

	@Override
	public boolean testParagraphBlock() {
		return blockTypes.contains(MarkdomBlockType.PARAGRAPH);
	}

	@Override
	public boolean testQuoteBlock() {
		return blockTypes.contains(MarkdomBlockType.QUOTE);
	}

	@Override
	public boolean testUnorderedListBlock() {
		return blockTypes.contains(MarkdomBlockType.UNORDERED_LIST);
	}

	@Override
	public boolean testCodeContent(String code) {
		return contentTypes.contains(MarkdomContentType.CODE);
	}

	@Override
	public boolean testEmphasisContent(MarkdomEmphasisLevel level) {
		return contentTypes.contains(MarkdomContentType.EMPHASIS);
	}

	@Override
	public boolean testImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		return contentTypes.contains(MarkdomContentType.IMAGE);
	}

	@Override
	public boolean testLineBreakContent(Boolean hard) {
		return contentTypes.contains(MarkdomContentType.LINE_BREAK);
	}

	@Override
	public boolean testLinkContent(String uri, Optional<String> title) {
		return contentTypes.contains(MarkdomContentType.LINK);
	}

	@Override
	public boolean testTextContent(String text) {
		return contentTypes.contains(MarkdomContentType.TEXT);
	}

}
