package io.markdom.handler;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.FunctionUtil;
import io.markdom.util.ObjectHelper;

public class SimpleMarkdomAudit implements MarkdomAudit {

	private final Set<MarkdomBlockType> blockTypes = EnumSet.noneOf(MarkdomBlockType.class);

	private final Set<MarkdomContentType> contentTypes = EnumSet.noneOf(MarkdomContentType.class);

	private final Consumer<MarkdomBlockType> blockTypeConsumer;

	private final Consumer<MarkdomContentType> contentTypeConsumer;

	public SimpleMarkdomAudit() {
		this(FunctionUtil.idleConsumer(), FunctionUtil.idleConsumer());
	}

	public SimpleMarkdomAudit(Consumer<MarkdomBlockType> blockTypeConsumer, Consumer<MarkdomContentType> contentTypeConsumer) {
		this.blockTypeConsumer = blockTypeConsumer(ObjectHelper.notNull("block type consumer", blockTypeConsumer));
		this.contentTypeConsumer = contentTypeConsumer(ObjectHelper.notNull("content type consumer", contentTypeConsumer));
	}

	private Consumer<MarkdomBlockType> blockTypeConsumer(Consumer<MarkdomBlockType> consumer) {
		return type -> {
			consumer.accept(type);
			blockTypes.add(type);
		};
	}

	private Consumer<MarkdomContentType> contentTypeConsumer(Consumer<MarkdomContentType> consumer) {
		return type -> {
			consumer.accept(type);
			contentTypes.add(type);
		};
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
		blockTypeConsumer.accept(MarkdomBlockType.CODE);
	}

	@Override
	public void onCommentBlock() {
		blockTypeConsumer.accept(MarkdomBlockType.COMMENT);
	}

	@Override
	public void onDivisionBlock() {
		blockTypeConsumer.accept(MarkdomBlockType.DIVISION);
	}

	@Override
	public void onHeadingBlock(MarkdomHeadingLevel level) {
		blockTypeConsumer.accept(MarkdomBlockType.HEADING);
	}

	@Override
	public void onOrderedListBlock(Integer startIndex) {
		blockTypeConsumer.accept(MarkdomBlockType.ORDERED_LIST);
	}

	@Override
	public void onParagraphBlock() {
		blockTypeConsumer.accept(MarkdomBlockType.PARAGRAPH);
	}

	@Override
	public void onQuoteBlock() {
		blockTypeConsumer.accept(MarkdomBlockType.QUOTE);
	}

	@Override
	public void onUnorderedListBlock() {
		blockTypeConsumer.accept(MarkdomBlockType.UNORDERED_LIST);
	}

	@Override
	public void onCodeContent(String code) {
		contentTypeConsumer.accept(MarkdomContentType.CODE);
	}

	@Override
	public void onEmphasisContent(MarkdomEmphasisLevel level) {
		contentTypeConsumer.accept(MarkdomContentType.EMPHASIS);
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		contentTypeConsumer.accept(MarkdomContentType.IMAGE);
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		contentTypeConsumer.accept(MarkdomContentType.LINE_BREAK);
	}

	@Override
	public void onLinkContent(String uri, Optional<String> title) {
		contentTypeConsumer.accept(MarkdomContentType.LINK);
	}

	@Override
	public void onTextContent(String text) {
		contentTypeConsumer.accept(MarkdomContentType.TEXT);
	}

	public Set<MarkdomBlockType> getBlockTypes() {
		return EnumSet.copyOf(blockTypes);
	}

	public Set<MarkdomContentType> getContentTypes() {
		return EnumSet.copyOf(contentTypes);
	}

}
