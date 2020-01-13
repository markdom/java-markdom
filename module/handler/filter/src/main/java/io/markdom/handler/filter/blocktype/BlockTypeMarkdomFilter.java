package io.markdom.handler.filter.blocktype;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.handler.AbstractMarkdomFilter;
import io.markdom.util.ObjectHelper;

public final class BlockTypeMarkdomFilter extends AbstractMarkdomFilter {

	private final BlockTypeMarkdomFilterHandler handler;

	public BlockTypeMarkdomFilter(BlockTypeMarkdomFilterHandler handler) {
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

}
