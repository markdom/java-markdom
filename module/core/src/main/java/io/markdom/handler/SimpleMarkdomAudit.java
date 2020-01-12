package io.markdom.handler;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;

public class SimpleMarkdomAudit implements MarkdomAudit {

	private final Set<MarkdomBlockType> blockTypes = EnumSet.noneOf(MarkdomBlockType.class);

	private final Set<MarkdomContentType> contentTypes = EnumSet.noneOf(MarkdomContentType.class);

	public SimpleMarkdomAudit() {
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
		blockTypes.add(MarkdomBlockType.CODE);
	}

	@Override
	public void onCommentBlock() {
		blockTypes.add(MarkdomBlockType.COMMENT);
	}

	@Override
	public void onDivisionBlock() {
		blockTypes.add(MarkdomBlockType.DIVISION);
	}

	@Override
	public void onHeadingBlock(MarkdomHeadingLevel level) {
		blockTypes.add(MarkdomBlockType.HEADING);
	}

	@Override
	public void onOrderedListBlock(Integer startIndex) {
		blockTypes.add(MarkdomBlockType.ORDERED_LIST);
	}

	@Override
	public void onParagraphBlock() {
		blockTypes.add(MarkdomBlockType.PARAGRAPH);
	}

	@Override
	public void onQuoteBlock() {
		blockTypes.add(MarkdomBlockType.QUOTE);
	}

	@Override
	public void onUnorderedListBlock() {
		blockTypes.add(MarkdomBlockType.UNORDERED_LIST);
	}

	@Override
	public void onCodeContent(String code) {
		contentTypes.add(MarkdomContentType.CODE);
	}

	@Override
	public void onEmphasisContent(MarkdomEmphasisLevel level) {
		contentTypes.add(MarkdomContentType.EMPHASIS);
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		contentTypes.add(MarkdomContentType.IMAGE);
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		contentTypes.add(MarkdomContentType.LINE_BREAK);
	}

	@Override
	public void onLinkContent(String uri, Optional<String> title) {
		contentTypes.add(MarkdomContentType.LINK);
	}

	@Override
	public void onTextContent(String text) {
		contentTypes.add(MarkdomContentType.TEXT);
	}

	public Set<MarkdomBlockType> getBlockTypes() {
		return EnumSet.copyOf(blockTypes);
	}

	public Set<MarkdomContentType> getContentTypes() {
		return EnumSet.copyOf(contentTypes);
	}

}
