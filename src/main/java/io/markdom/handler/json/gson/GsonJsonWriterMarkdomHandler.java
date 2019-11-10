package io.markdom.handler.json.gson;

import java.util.Optional;

import com.google.gson.stream.JsonWriter;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.common.MarkdomKeys;
import io.markdom.common.MarkdomSchemas;
import io.markdom.handler.AbstractMarkdomHandler;
import io.markdom.util.ObjectHelper;
import lombok.SneakyThrows;

public final class GsonJsonWriterMarkdomHandler extends AbstractMarkdomHandler<Void> {

	private final JsonWriter writer;

	private final boolean includeSchema;

	public GsonJsonWriterMarkdomHandler(JsonWriter writer) {
		this(writer, false);
	}

	public GsonJsonWriterMarkdomHandler(JsonWriter writer, boolean includeSchema) {
		this.writer = ObjectHelper.notNull("writer", writer);
		this.includeSchema = includeSchema;
	}

	@Override
	@SneakyThrows
	public void onDocumentBegin() {
		writer.beginObject();
		if (includeSchema) {
			writer.name("$schema");
			writer.value(MarkdomSchemas.JSON);
		}
		writer.name(MarkdomKeys.VERSION);
		writer.value("1.0");
		writer.name(MarkdomKeys.BLOCKS);
		writer.beginArray();
	}

	@Override
	@SneakyThrows
	public void onDocumentEnd() {
		writer.endArray();
		writer.endObject();
		writer.flush();
	}

	@Override
	@SneakyThrows
	public void onBlockBegin(MarkdomBlockType type) {
		writer.beginObject();
		writer.name(MarkdomKeys.TYPE);
		writer.value(type.toName());
	}

	@Override
	@SneakyThrows
	public void onCodeBlock(String code, Optional<String> hint) {
		writer.name(MarkdomKeys.CODE);
		writer.value(code);
		if (hint.isPresent()) {
			writer.name(MarkdomKeys.HINT);
			writer.value(hint.get());
		}
	}

	@Override
	@SneakyThrows
	public void onCommentBlock(String comment) {
		writer.name(MarkdomKeys.COMMENT);
		writer.value(comment);
	}

	@Override
	@SneakyThrows
	public void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		writer.name(MarkdomKeys.LEVEL);
		writer.value(level.ordinal() + 1);
	}

	@Override
	@SneakyThrows
	public void onUnorderedListBlockBegin() {
		writer.name(MarkdomKeys.ITEMS);
		writer.beginArray();
	}

	@Override
	@SneakyThrows
	public void onOrderedListBlockBegin(Integer startIndex) {
		writer.name(MarkdomKeys.START_INDEX);
		writer.value(startIndex);
		writer.name(MarkdomKeys.ITEMS);
		writer.beginArray();
	}

	@Override
	@SneakyThrows
	public void onListItemBegin() {
		writer.beginObject();
		writer.name(MarkdomKeys.BLOCKS);
		writer.beginArray();
	}

	@Override
	@SneakyThrows
	public void onListItemEnd() {
		writer.endArray();
		writer.endObject();
	}

	@Override
	@SneakyThrows
	public void onUnorderedListBlockEnd() {
		writer.endArray();
	}

	@Override
	@SneakyThrows
	public void onOrderedListBlockEnd(Integer startIndex) {
		writer.endArray();
	}

	@Override
	@SneakyThrows
	public void onQuoteBlockBegin() {
		writer.name(MarkdomKeys.BLOCKS);
		writer.beginArray();
	}

	@Override
	@SneakyThrows
	public void onQuoteBlockEnd() {
		writer.endArray();
	}

	@Override
	@SneakyThrows
	public void onBlockEnd(MarkdomBlockType type) {
		writer.endObject();
	}

	@Override
	@SneakyThrows
	public void onContentsBegin() {
		writer.name(MarkdomKeys.CONTENTS);
		writer.beginArray();
	}

	@Override
	@SneakyThrows
	public void onContentBegin(MarkdomContentType type) {
		writer.beginObject();
		writer.name(MarkdomKeys.TYPE);
		writer.value(type.toName());
	}

	@Override
	@SneakyThrows
	public void onCodeContent(String code) {
		writer.name(MarkdomKeys.CODE);
		writer.value(code);
	}

	@Override
	@SneakyThrows
	public void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		writer.name(MarkdomKeys.LEVEL);
		writer.value(level.ordinal() + 1);
	}

	@Override
	@SneakyThrows
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		writer.name(MarkdomKeys.URI);
		writer.value(uri);
		if (title.isPresent()) {
			writer.name(MarkdomKeys.TITLE);
			writer.value(title.get());
		}
		if (alternative.isPresent()) {
			writer.name(MarkdomKeys.ALTERNATIVE);
			writer.value(alternative.get());
		}
	}

	@Override
	@SneakyThrows
	public void onLineBreakContent(Boolean hard) {
		writer.name(MarkdomKeys.HARD);
		writer.value(hard);
	}

	@Override
	@SneakyThrows
	public void onLinkContentBegin(String uri, Optional<String> title) {
		writer.name(MarkdomKeys.URI);
		writer.value(uri);
		if (title.isPresent()) {
			writer.name(MarkdomKeys.TITLE);
			writer.value(title.get());
		}
	}

	@Override
	@SneakyThrows
	public void onTextContent(String text) {
		writer.name(MarkdomKeys.TEXT);
		writer.value(text);
	}

	@Override
	@SneakyThrows
	public void onContentEnd(MarkdomContentType type) {
		writer.endObject();
	}

	@Override
	@SneakyThrows
	public void onContentsEnd() {
		writer.endArray();
	}

	@Override
	public Void getResult() {
		return null;
	}

}
