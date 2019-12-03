package io.markdom.handler.text.commonmark;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.handler.MarkdomHandler;
import io.markdom.util.ObjectHelper;

public final class CommonmarkTextMarkdomHandler<ActualAppendable extends Appendable> implements MarkdomHandler<ActualAppendable> {

	private static final EmptySection EMPTY_SECTION = new EmptySection();

	private final CommonmarkTextConfiguration configuration;

	private final ActualAppendable appendable;

	private final IndentationStack indentationStack;

	private final IndentationAppendable indentationAppendable;

	private MarkdomBlockType lastType;

	private boolean emptyBlocks;

	private ContentBuffer buffer;

	private Section section;

	public CommonmarkTextMarkdomHandler(ActualAppendable appendable) {
		this(CommonmarkTextConfiguration.builder().build(), appendable);
	}

	public CommonmarkTextMarkdomHandler(CommonmarkTextConfiguration configuration, ActualAppendable appendable) {
		this.configuration = ObjectHelper.notNull("configuration", configuration);
		this.appendable = ObjectHelper.notNull("appendable", appendable);
		this.indentationStack = new IndentationStack();
		indentationAppendable = new IndentationAppendable(configuration, indentationStack, appendable);
	}

	@Override
	public void onDocumentBegin() {
	}

	@Override
	public void onBlocksBegin() {
		emptyBlocks = true;
	}

	@Override
	public void onBlockBegin(MarkdomBlockType type) {
		emptyBlocks = false;
		if (null != lastType) {
			EMPTY_SECTION.appendTo(indentationAppendable);
			if (isListBlock(type) && isListBlock(lastType)) {
				new CommentBlockSection("foo").appendTo(indentationAppendable);
				EMPTY_SECTION.appendTo(indentationAppendable);
			}
		}
	}

	private boolean isListBlock(MarkdomBlockType type) {
		return MarkdomBlockType.ORDERED_LIST == type || MarkdomBlockType.UNORDERED_LIST == type;
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
		switch (configuration.getCodeBlockOption()) {
			case FENCED:
				beginSection(new FencedCodeBlockSection(configuration, code, hint));
				break;
			case INDENTED:
				beginSection(new IndentedCodeBlockSection(code));
				break;
		}
		endSection(false);
	}

	@Override
	public void onCommentBlock(String comment) {
		section = new CommentBlockSection(comment);
		endSection(false);
	}

	@Override
	public void onDivisionBlock() {
		beginSection(new DivisionBlockSection(configuration));
		endSection(false);
	}

	@Override
	public void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		beginContentSection(new HeadingBlockSection(configuration, level));
	}

	@Override
	public void onHeadingBlockEnd(MarkdomHeadingLevel level) {
		endSection(false);
	}

	@Override
	public void onOrderedListBlockBegin(Integer startIndex) {
		indentationStack.push(new OrderedListIndentation(configuration, startIndex));
	}

	@Override
	public void onOrderedListBlockEnd(Integer startIndex) {
		indentationStack.pop();
	}

	@Override
	public void onParagraphBlockBegin() {
		beginContentSection(new ParagraphSection(configuration));
	}

	@Override
	public void onParagraphBlockEnd() {
		endSection(false);
	}

	@Override
	public void onQuoteBlockBegin() {
		indentationStack.push(new QuoteIndentation());
	}

	@Override
	public void onQuoteBlockEnd() {
		indentationStack.pop();
	}

	@Override
	public void onUnorderedListBlockBegin() {
		indentationStack.push(new UnorderedListIndentation(configuration));
	}

	@Override
	public void onUnorderedListBlockEnd() {
		indentationStack.pop();
	}

	@Override
	public void onBlockEnd(MarkdomBlockType type) {
		lastType = type;
	}

	@Override
	public void onNextBlock() {
	}

	@Override
	public void onBlocksEnd() {
		if (emptyBlocks) {
			EMPTY_SECTION.appendTo(indentationAppendable);
		}
	}

	@Override
	public void onListItemsBegin() {
	}

	@Override
	public void onListItemBegin() {
		lastType = null;
	}

	@Override
	public void onListItemEnd() {
		indentationStack.advance();
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
	public void onCodeContent(String code) {
		buffer.appendCode(code);
	}

	@Override
	public void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		buffer.appendEmphasisBegin(level);
	}

	@Override
	public void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
		buffer.appendEmphasisEnd(level);
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		buffer.appendImage(uri, title, alternative);
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		buffer.appendLineBreak(hard);
	}

	@Override
	public void onLinkContentBegin(String uri, Optional<String> title) {
		buffer.appendLinkBegin(uri);
	}

	@Override
	public void onLinkContentEnd(String uri, Optional<String> title) {
		buffer.appendLinkEnd(uri, title);
	}

	@Override
	public void onTextContent(String text) {
		buffer.appendText(text);
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
	public void onDocumentEnd() {
	}

	@Override
	public ActualAppendable getResult() {
		return appendable;
	}

	private void beginContentSection(ContentSection section) {
		this.buffer = section.getBuffer();
		beginSection(section);
	}

	private void beginSection(Section section) {
		this.section = section;
	}

	private void endSection(boolean listBlock) {
		section.appendTo(indentationAppendable);
	}

}
