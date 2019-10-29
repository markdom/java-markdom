package io.markdom.handler;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;

/**
 * An {@link AbstractMarkdomHandler} is an abstract {@link MarkdomHandler} that does nothing.
 * 
 * An {@link AbstractMarkdomHandler} is intended as a base class for specific {@link MarkdomHandler}s that don't need to
 * implement all callback methods.
 */
public abstract class AbstractMarkdomHandler<Result> implements MarkdomHandler<Result> {

	@Override
	public void onDocumentBegin() {
	}

	@Override
	public void onDocumentEnd() {
	}

	@Override
	public void onBlocksBegin() {
	}

	@Override
	public void onBlockBegin(MarkdomBlockType type) {
	}

	@Override
	public void onBlockEnd(MarkdomBlockType type) {
	}

	@Override
	public void onNextBlock() {
	}

	@Override
	public void onBlocksEnd() {
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
	}

	@Override
	public void onCommentBlock(String comment) {
	}

	@Override
	public void onDivisionBlock() {
	}

	@Override
	public void onHeadingBlockBegin(MarkdomHeadingLevel level) {
	}

	@Override
	public void onHeadingBlockEnd(MarkdomHeadingLevel level) {
	}

	@Override
	public void onOrderedListBlockBegin(Integer startIndex) {
	}

	@Override
	public void onOrderedListBlockEnd(Integer startIndex) {
	}

	@Override
	public void onParagraphBlockBegin() {
	}

	@Override
	public void onParagraphBlockEnd() {
	}

	@Override
	public void onQuoteBlockBegin() {
	}

	@Override
	public void onQuoteBlockEnd() {
	}

	@Override
	public void onUnorderedListBlockBegin() {
	}

	@Override
	public void onUnorderedListBlockEnd() {
	}

	@Override
	public void onListItemsBegin() {
	}

	@Override
	public void onListItemBegin() {
	}

	@Override
	public void onListItemEnd() {
	}

	@Override
	public void onNextListItem() {
	}

	@Override
	public void onListItemsEnd() {
	}

	@Override
	public void onContentsBegin() {
	}

	@Override
	public void onContentBegin(MarkdomContentType type) {
	}

	@Override
	public void onContentEnd(MarkdomContentType type) {
	}

	@Override
	public void onNextContent() {
	}

	@Override
	public void onContentsEnd() {
	}

	@Override
	public void onCodeContent(String code) {
	}

	@Override
	public void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
	}

	@Override
	public void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
	}

	@Override
	public void onLinkContentBegin(String uri, Optional<String> title) {
	}

	@Override
	public void onLinkContentEnd(String uri, Optional<String> title) {
	}

	@Override
	public void onTextContent(String text) {
	}

	@Override
	public Result getResult() {
		return null;
	}

}
