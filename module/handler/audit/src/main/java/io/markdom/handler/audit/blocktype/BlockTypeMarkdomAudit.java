package io.markdom.handler.audit.blocktype;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.handler.AbstractMarkdomAudit;
import io.markdom.util.ObjectHelper;

public final class BlockTypeMarkdomAudit extends AbstractMarkdomAudit {

	private final BlockTypeMarkdomAuditHandler handler;

	public BlockTypeMarkdomAudit(BlockTypeMarkdomAuditHandler handler) {
		this.handler = ObjectHelper.notNull("handler", handler);
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
		handler.onBlockType(MarkdomBlockType.CODE);
	}

	@Override
	public void onCommentBlock() {
		handler.onBlockType(MarkdomBlockType.COMMENT);
	}

	@Override
	public void onDivisionBlock() {
		handler.onBlockType(MarkdomBlockType.DIVISION);
	}

	@Override
	public void onHeadingBlock(MarkdomHeadingLevel level) {
		handler.onBlockType(MarkdomBlockType.HEADING);
	}

	@Override
	public void onOrderedListBlock(Integer startIndex) {
		handler.onBlockType(MarkdomBlockType.ORDERED_LIST);
	}

	@Override
	public void onParagraphBlock() {
		handler.onBlockType(MarkdomBlockType.PARAGRAPH);
	}

	@Override
	public void onQuoteBlock() {
		handler.onBlockType(MarkdomBlockType.QUOTE);
	}

	@Override
	public void onUnorderedListBlock() {
		handler.onBlockType(MarkdomBlockType.UNORDERED_LIST);
	}

}
