package io.markdom.handler.json;

import java.util.Optional;
import java.util.Stack;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.common.MarkdomKeys;
import io.markdom.common.MarkdomSchemas;
import io.markdom.handler.MarkdomHandler;

public abstract class AbstractJsonObjectMarkdomHandler<JsonObject, JsonArray, Result> implements MarkdomHandler<Result> {

	private final JsonObject jsonObject = createObject();

	private final Stack<JsonObject> jsonObjects = new Stack<>();

	private final Stack<JsonArray> jsonArrays = new Stack<>();

	private final boolean includeSchema;

	private JsonObject currentObject;

	private JsonArray currentArray;

	public AbstractJsonObjectMarkdomHandler(boolean includeSchema) {
		this.includeSchema = includeSchema;
	}

	@Override
	public final void onDocumentBegin() {
		currentObject = jsonObject;
		if (includeSchema) {
			putString(currentObject, "$schema", MarkdomSchemas.JSON);
		}
		putString(currentObject, MarkdomKeys.VERSION, "1.0");
	}

	@Override
	public final void onBlocksBegin() {
		openArray();
	}

	@Override
	public final void onBlockBegin(MarkdomBlockType type) {
		openObject();
		putString(currentObject, MarkdomKeys.TYPE, type.toName());
	}

	@Override
	public final void onCodeBlock(String code, Optional<String> hint) {
		putString(currentObject, MarkdomKeys.CODE, code);
		if (hint.isPresent()) {
			putString(currentObject, MarkdomKeys.HINT, hint.get());
		}
	}

	@Override
	public final void onCommentBlock(String comment) {
		putString(currentObject, MarkdomKeys.COMMENT, comment);
	}

	@Override
	public final void onDivisionBlock() {
	}

	@Override
	public final void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		putInteger(currentObject, MarkdomKeys.LEVEL, level.ordinal() + 1);
	}

	@Override
	public final void onHeadingBlockEnd(MarkdomHeadingLevel level) {
	}

	@Override
	public final void onUnorderedListBlockBegin() {
	}

	@Override
	public final void onOrderedListBlockBegin(Integer startIndex) {
		putInteger(currentObject, MarkdomKeys.START_INDEX, startIndex);
	}

	@Override
	public final void onUnorderedListBlockEnd() {
	}

	@Override
	public final void onOrderedListBlockEnd(Integer startIndex) {
	}

	@Override
	public final void onParagraphBlockBegin() {
	}

	@Override
	public final void onParagraphBlockEnd() {
	}

	@Override
	public final void onQuoteBlockBegin() {
	}

	@Override
	public final void onQuoteBlockEnd() {
	}

	@Override
	public final void onBlockEnd(MarkdomBlockType type) {
		addObject(currentArray, currentObject);
		closeObject();
	}

	@Override
	public final void onNextBlock() {
	}

	@Override
	public final void onBlocksEnd() {
		putArray(currentObject, MarkdomKeys.BLOCKS, currentArray);
		closeArray();
	}

	@Override
	public final void onListItemsBegin() {
		openArray();
	}

	@Override
	public final void onListItemBegin() {
		openObject();
	}

	@Override
	public final void onListItemEnd() {
		addObject(currentArray, currentObject);
		closeObject();
	}

	@Override
	public final void onNextListItem() {
	}

	@Override
	public final void onListItemsEnd() {
		putArray(currentObject, MarkdomKeys.ITEMS, currentArray);
		closeArray();
	}

	@Override
	public final void onContentsBegin() {
		openArray();
	}

	@Override
	public final void onContentBegin(MarkdomContentType type) {
		openObject();
		putString(currentObject, MarkdomKeys.TYPE, type.toName());
	}

	@Override
	public final void onCodeContent(String code) {
		putString(currentObject, MarkdomKeys.CODE, code);
	}

	@Override
	public final void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		putInteger(currentObject, MarkdomKeys.LEVEL, level.ordinal() + 1);
	}

	@Override
	public final void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
	}

	@Override
	public final void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		putString(currentObject, MarkdomKeys.URI, uri.toString());
		if (title.isPresent()) {
			putString(currentObject, MarkdomKeys.TITLE, title.get());
		}
		if (alternative.isPresent()) {
			putString(currentObject, MarkdomKeys.ALTERNATIVE, alternative.get());
		}
	}

	@Override
	public final void onLineBreakContent(Boolean hard) {
		putBoolean(currentObject, MarkdomKeys.HARD, hard);
	}

	@Override
	public final void onLinkContentBegin(String uri, Optional<String> title) {
		putString(currentObject, MarkdomKeys.URI, uri);
		if (title.isPresent()) {
			putString(currentObject, MarkdomKeys.TITLE, title.get());
		}
	}

	@Override
	public final void onLinkContentEnd(String uri, Optional<String> title) {
	}

	@Override
	public final void onTextContent(String text) {
		putString(currentObject, MarkdomKeys.TEXT, text);
	}

	@Override
	public final void onContentEnd(MarkdomContentType type) {
		addObject(currentArray, currentObject);
		closeObject();
	}

	@Override
	public final void onNextContent() {
	}

	@Override
	public final void onContentsEnd() {
		putArray(currentObject, MarkdomKeys.CONTENTS, currentArray);
		closeArray();
	}

	@Override
	public final Result getResult() {
		return toResult(jsonObject);
	}

	private JsonObject openObject() {
		jsonObjects.push(currentObject);
		currentObject = createObject();
		return currentObject;
	}

	private void closeObject() {
		currentObject = jsonObjects.pop();
	}

	private JsonArray openArray() {
		jsonArrays.push(currentArray);
		currentArray = createArray();
		return currentArray;
	}

	private void closeArray() {
		currentArray = jsonArrays.pop();
	}

	@Override
	public final void onDocumentEnd() {
	}

	protected abstract JsonObject createObject();

	protected abstract JsonArray createArray();

	protected abstract void putBoolean(JsonObject object, String key, Boolean value);

	protected abstract void putInteger(JsonObject object, String key, Integer value);

	protected abstract void putString(JsonObject object, String key, String value);

	protected abstract void putArray(JsonObject object, String key, JsonArray value);

	protected abstract void addObject(JsonArray array, JsonObject object);

	protected abstract Result toResult(JsonObject object);

}