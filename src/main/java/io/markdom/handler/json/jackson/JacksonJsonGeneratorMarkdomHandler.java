package io.markdom.handler.json.jackson;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonGenerator;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.common.MarkdomKeys;
import io.markdom.common.MarkdomSchemas;
import io.markdom.handler.MarkdomHandler;
import io.markdom.util.ObjectHelper;
import lombok.SneakyThrows;

public final class JacksonJsonGeneratorMarkdomHandler implements MarkdomHandler<Void> {

	private final JsonGenerator generator;

	private final boolean includeSchema;

	public JacksonJsonGeneratorMarkdomHandler(JsonGenerator generator) {
		this(generator, false);
	}

	public JacksonJsonGeneratorMarkdomHandler(JsonGenerator generator, boolean includeSchema) {
		this.generator = ObjectHelper.notNull("generator", generator);
		this.includeSchema = includeSchema;
	}

	@Override
	@SneakyThrows
	public void onDocumentBegin() {
		generator.writeStartObject();
		if (includeSchema) {
			generator.writeFieldName("$schema");
			generator.writeString(MarkdomSchemas.JSON);
		}
		generator.writeFieldName(MarkdomKeys.VERSION);
		generator.writeString("1.0");
		generator.writeFieldName(MarkdomKeys.BLOCKS);
		generator.writeStartArray();
	}

	@Override
	public void onBlocksBegin() {
	}

	@Override
	@SneakyThrows
	public void onBlockBegin(MarkdomBlockType type) {
		generator.writeStartObject();
		generator.writeFieldName(MarkdomKeys.TYPE);
		generator.writeString(type.toName());
	}

	@Override
	@SneakyThrows
	public void onCodeBlock(String code, Optional<String> hint) {
		generator.writeFieldName(MarkdomKeys.CODE);
		generator.writeString(code);
		if (hint.isPresent()) {
			generator.writeFieldName(MarkdomKeys.HINT);
			generator.writeString(hint.get());
		}
	}

	@Override
	@SneakyThrows
	public void onCommentBlock(String comment) {
		generator.writeFieldName(MarkdomKeys.COMMENT);
		generator.writeString(comment);
	}

	@Override
	public void onDivisionBlock() {
	}

	@Override
	@SneakyThrows
	public void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		generator.writeFieldName(MarkdomKeys.LEVEL);
		generator.writeNumber(level.ordinal() + 1);
	}

	@Override
	public void onHeadingBlockEnd(MarkdomHeadingLevel level) {
	}

	@Override
	@SneakyThrows
	public void onOrderedListBlockBegin(Integer startIndex) {
		generator.writeFieldName(MarkdomKeys.START_INDEX);
		generator.writeNumber(startIndex);
		generator.writeFieldName(MarkdomKeys.ITEMS);
		generator.writeStartArray();
	}

	@Override
	@SneakyThrows
	public void onOrderedListBlockEnd(Integer startIndex) {
		generator.writeEndArray();
	}

	@Override
	public void onParagraphBlockBegin() {
	}

	@Override
	public void onParagraphBlockEnd() {
	}

	@Override
	@SneakyThrows
	public void onQuoteBlockBegin() {
		generator.writeFieldName(MarkdomKeys.BLOCKS);
		generator.writeStartArray();
	}

	@Override
	@SneakyThrows
	public void onQuoteBlockEnd() {
		generator.writeEndArray();
	}

	@Override
	@SneakyThrows
	public void onUnorderedListBlockBegin() {
		generator.writeFieldName(MarkdomKeys.ITEMS);
		generator.writeStartArray();
	}

	@Override
	@SneakyThrows
	public void onUnorderedListBlockEnd() {
		generator.writeEndArray();
	}

	@Override
	@SneakyThrows
	public void onBlockEnd(MarkdomBlockType type) {
		generator.writeEndObject();
	}

	@Override
	public void onNextBlock() {
	}

	@Override
	public void onBlocksEnd() {
	}

	@Override
	public void onListItemsBegin() {
	}

	@Override
	@SneakyThrows
	public void onListItemBegin() {
		generator.writeStartObject();
		generator.writeFieldName(MarkdomKeys.BLOCKS);
		generator.writeStartArray();
	}

	@Override
	@SneakyThrows
	public void onListItemEnd() {
		generator.writeEndArray();
		generator.writeEndObject();
	}

	@Override
	public void onNextListItem() {
	}

	@Override
	public void onListItemsEnd() {
	}

	@Override
	@SneakyThrows
	public void onContentsBegin() {
		generator.writeFieldName(MarkdomKeys.CONTENTS);
		generator.writeStartArray();
	}

	@Override
	@SneakyThrows
	public void onContentBegin(MarkdomContentType type) {
		generator.writeStartObject();
		generator.writeFieldName(MarkdomKeys.TYPE);
		generator.writeString(type.toName());
	}

	@Override
	@SneakyThrows
	public void onCodeContent(String code) {
		generator.writeFieldName(MarkdomKeys.CODE);
		generator.writeString(code);
	}

	@Override
	@SneakyThrows
	public void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		generator.writeFieldName(MarkdomKeys.LEVEL);
		generator.writeNumber(level.ordinal() + 1);
	}

	@Override
	public void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
	}

	@Override
	@SneakyThrows
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		generator.writeFieldName(MarkdomKeys.URI);
		generator.writeString(uri);
		if (title.isPresent()) {
			generator.writeFieldName(MarkdomKeys.TITLE);
			generator.writeString(title.get());
		}
		if (alternative.isPresent()) {
			generator.writeFieldName(MarkdomKeys.ALTERNATIVE);
			generator.writeString(alternative.get());
		}
	}

	@Override
	@SneakyThrows
	public void onLineBreakContent(Boolean hard) {
		generator.writeFieldName(MarkdomKeys.HARD);
		generator.writeBoolean(hard);
	}

	@Override
	@SneakyThrows
	public void onLinkContentBegin(String uri, Optional<String> title) {
		generator.writeFieldName(MarkdomKeys.URI);
		generator.writeString(uri);
		if (title.isPresent()) {
			generator.writeFieldName(MarkdomKeys.TITLE);
			generator.writeString(title.get());
		}
	}

	@Override
	public void onLinkContentEnd(String uri, Optional<String> title) {
	}

	@Override
	@SneakyThrows
	public void onTextContent(String text) {
		generator.writeFieldName(MarkdomKeys.TEXT);
		generator.writeString(text);
	}

	@Override
	@SneakyThrows
	public void onContentEnd(MarkdomContentType type) {
		generator.writeEndObject();
	}

	@Override
	public void onNextContent() {
	}

	@Override
	@SneakyThrows
	public void onContentsEnd() {
		generator.writeEndArray();
	}

	@Override
	@SneakyThrows
	public void onDocumentEnd() {
		generator.writeEndArray();
		generator.writeEndObject();
		generator.flush();
	}

	@Override
	public Void getResult() {
		return null;
	}

}
