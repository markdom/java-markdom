package io.markdom.handler.json;

import java.util.Iterator;
import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomException;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.common.MarkdomKeys;
import io.markdom.handler.MarkdomDispatcher;
import io.markdom.handler.MarkdomHandler;
import io.markdom.handler.SimpleMarkdomHandler;
import io.markdom.util.ObjectHelper;

public abstract class AbstractJsonObjectMarkdomDispatcher<JsonObject, JsonArray> implements MarkdomDispatcher {

	private SimpleMarkdomHandler<?> handler;

	@Override
	public final <Result> Result handle(MarkdomHandler<Result> handler) {
		this.handler = new SimpleMarkdomHandler<Result>(ObjectHelper.notNull("handler", handler));
		try {
			JsonObject documentObject = getRootObject();
			checkVersion(reqString(documentObject, MarkdomKeys.VERSION));
			document(documentObject);
			return handler.getResult();
		} catch (Exception e) {
			throw new MarkdomException(e);
		}
	}

	private void checkVersion(String version) {
		if (!"1.0".equals(version)) {
			throw new MarkdomException("Unsupported version: " + version);
		}
	}

	private void document(JsonObject documentObject) {
		handler.onDocumentBegin();
		blocks(optArray(documentObject, MarkdomKeys.BLOCKS));
		handler.onDocumentEnd();
	}

	private void blocks(JsonArray blocksArray) {
		getObjects(blocksArray).forEachRemaining(this::block);
	}

	private void block(JsonObject blockObject) {
		MarkdomBlockType type = MarkdomBlockType.fromName(reqString(blockObject, MarkdomKeys.TYPE));
		switch (type) {
			case CODE:
				codeBlock(blockObject);
				return;
			case COMMENT:
				commentBlock(blockObject);
				return;
			case DIVISION:
				divisionBlock(blockObject);
				return;
			case HEADING:
				headingBlock(blockObject);
				return;
			case ORDERED_LIST:
				orderedListBlock(blockObject);
				return;
			case PARAGRAPH:
				paragraphBlock(blockObject);
				return;
			case QUOTE:
				quoteBlock(blockObject);
				return;
			case UNORDERED_LIST:
				unorderedListBlock(blockObject);
				return;
		}
		throw new InternalError("Unexpected block type: " + type);
	}

	private void codeBlock(JsonObject codeObject) {
		String code = reqString(codeObject, MarkdomKeys.CODE);
		Optional<String> hint = optString(codeObject, MarkdomKeys.HINT);
		handler.onCodeBlock(code, hint);
	}

	private void commentBlock(JsonObject commentObject) {
		String comment = reqString(commentObject, MarkdomKeys.COMMENT);
		handler.onCommentBlock(comment);
	}

	private void divisionBlock(JsonObject divisionObject) {
		handler.onDivisionBlock();
	}

	private void headingBlock(JsonObject headingObject) {
		MarkdomHeadingLevel level = MarkdomHeadingLevel.values()[reqInteger(headingObject, MarkdomKeys.LEVEL) - 1];
		handler.onHeadingBlockBegin(level);
		contents(optArray(headingObject, MarkdomKeys.CONTENTS));
		handler.onHeadingBlockEnd();
	}

	private void orderedListBlock(JsonObject orderedListObject) {
		int startIndex = reqInteger(orderedListObject, MarkdomKeys.START_INDEX);
		handler.onOrderedListBlockBegin(startIndex);
		items(optArray(orderedListObject, MarkdomKeys.ITEMS));
		handler.onOrderedListBlockEnd();
	}

	private void paragraphBlock(JsonObject paragraphObject) {
		handler.onParagraphBlockBegin();
		contents(optArray(paragraphObject, MarkdomKeys.CONTENTS));
		handler.onParagraphBlockEnd();
	}

	private void quoteBlock(JsonObject quoteObject) {
		handler.onQuoteBlockBegin();
		blocks(optArray(quoteObject, MarkdomKeys.BLOCKS));
		handler.onQuoteBlockEnd();
	}

	private void unorderedListBlock(JsonObject unorderedListObject) {
		handler.onUnorderedListBlockBegin();
		items(optArray(unorderedListObject, MarkdomKeys.ITEMS));
		handler.onUnorderedListBlockEnd();
	}

	private void items(JsonArray itemsArray) {
		getObjects(itemsArray).forEachRemaining(this::item);
	}

	private void item(JsonObject itemObject) {
		handler.onListItemBegin();
		blocks(optArray(itemObject, MarkdomKeys.BLOCKS));
		handler.onListItemEnd();
	}

	private void contents(JsonArray contentsArray) {
		getObjects(contentsArray).forEachRemaining(this::content);
	}

	private void content(JsonObject contentObject) {
		MarkdomContentType type = MarkdomContentType.fromName(reqString(contentObject, MarkdomKeys.TYPE));
		switch (type) {
			case CODE:
				codeContent(contentObject);
				return;
			case EMPHASIS:
				emphasisContent(contentObject);
				return;
			case IMAGE:
				imageContent(contentObject);
				return;
			case LINE_BREAK:
				lineBreakContent(contentObject);
				return;
			case LINK:
				linkContent(contentObject);
				return;
			case TEXT:
				textContent(contentObject);
				return;
		}
		throw new InternalError("Unexpected content type: " + type);
	}

	private void codeContent(JsonObject codeObject) {
		String code = reqString(codeObject, MarkdomKeys.CODE);
		handler.onCodeContent(code);
	}

	private void emphasisContent(JsonObject emphasisObject) {
		MarkdomEmphasisLevel level = MarkdomEmphasisLevel.values()[reqInteger(emphasisObject, MarkdomKeys.LEVEL) - 1];
		handler.onEmphasisContentBegin(level);
		contents(optArray(emphasisObject, MarkdomKeys.CONTENTS));
		handler.onEmphasisContentEnd();
	}

	private void imageContent(JsonObject imageObject) {
		String uri = reqString(imageObject, MarkdomKeys.URI);
		Optional<String> title = optString(imageObject, MarkdomKeys.TITLE);
		Optional<String> alternative = optString(imageObject, MarkdomKeys.ALTERNATIVE);
		handler.onImageContent(uri, title, alternative);
	}

	private void lineBreakContent(JsonObject lineBreakObject) {
		boolean hard = reqBoolean(lineBreakObject, MarkdomKeys.HARD);
		handler.onLineBreakContent(hard);
	}

	private void linkContent(JsonObject linkObject) {
		String uri = reqString(linkObject, MarkdomKeys.URI);
		Optional<String> title = optString(linkObject, MarkdomKeys.TITLE);
		handler.onLinkContentBegin(uri, title);
		contents(optArray(linkObject, MarkdomKeys.CONTENTS));
		handler.onLinkContentEnd();
	}

	private void textContent(JsonObject textObject) {
		String text = reqString(textObject, MarkdomKeys.TEXT);
		handler.onTextContent(text);
	}

	protected abstract JsonObject getRootObject();

	protected abstract Iterator<JsonObject> getObjects(JsonArray jsonArray);

	protected abstract JsonArray optArray(JsonObject jsonArray, String key);

	protected abstract Optional<String> optString(JsonObject jsonObject, String key);

	protected abstract String reqString(JsonObject jsonObject, String key);

	protected abstract Boolean reqBoolean(JsonObject jsonObject, String key);

	protected abstract Integer reqInteger(JsonObject jsonObject, String key);

}
