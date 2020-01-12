package io.markdom.handler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Stack;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomException;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.ObjectHelper;
import lombok.Data;

public final class DebuggingMarkdomHandler<Result> implements MarkdomHandler<Result> {

	private static enum Context {

		DOCUMENT,

		HEADING_BLOCK,

		PARAGRAPH_BLOCK,

		QUOTE_BLOCK,

		LIST_ITEM,

		ORDERED_LIST,

		UNORDERED_LIST,

		EMPHASIS_CONTENT,

		LINK_CONTENT,

	}

	private static enum Method {

		ON_DOCUMENT_BEGIN,

		ON_DOCUMENT_END,

		ON_BLOCKS_BEGIN,

		ON_BLOCK_BEGIN,

		ON_BLOCK_END,

		ON_NEXT_BLOCK,

		ON_BLOCKS_END,

		ON_CODE_BLOCK,

		ON_COMMENT_BLOCK,

		ON_DIVISION_BLOCK,

		ON_HEADING_BLOCK_BEGIN,

		ON_HEADING_BLOCK_END,

		ON_ORDERED_LIST_BLOCK_BEGIN,

		ON_ORDERED_LIST_BLOCK_END,

		ON_PARAGRAPH_BLOCK_BEGIN,

		ON_PARAGRAPH_BLOCK_END,

		ON_QUOTE_BLOCK_BEGIN,

		ON_QUOTE_BLOCK_END,

		ON_UNORDERED_LIST_BLOCK_BEGIN,

		ON_UNORDERED_LIST_BLOCK_END,

		ON_LIST_ITEMS_BEGIN,

		ON_LIST_ITEM_BEGIN,

		ON_LIST_ITEM_END,

		ON_NEXT_LIST_ITEM,

		ON_LIST_ITEMS_END,

		ON_CONTENTS_BEGIN,

		ON_CONTENT_BEGIN,

		ON_CONTENT_END,

		ON_NEXT_CONTENT,

		ON_CONTENTS_END,

		ON_CODE_CONTENT,

		ON_EMPHASIS_CONTENT_BEGIN,

		ON_EMPHASIS_CONTENT_END,

		ON_IMAGE_CONTENT,

		ON_LINE_BREAK_CONTENT,

		ON_LINK_CONTENT_BEGIN,

		ON_LINK_CONTENT_END,

		ON_TEXT_CONTENT,

		ON_RESULT;

	}

	@Data
	private static class Parameter<Payload> {

		private final Payload payload;

	}

	private final MarkdomHandler<Result> handler;

	private final Stack<Context> contexts = new Stack<Context>();

	private final Stack<Parameter<?>> parameters = new Stack<Parameter<?>>();

	private final EnumSet<Method> expectedMethods = EnumSet.noneOf(Method.class);

	public DebuggingMarkdomHandler(MarkdomHandler<Result> handler) {
		this.handler = ObjectHelper.notNull("handler", handler);
		expectMethod(Method.ON_DOCUMENT_BEGIN);
	}

	@Override
	public void onDocumentBegin() {
		isExpectedMethod(Method.ON_DOCUMENT_BEGIN);
		contexts.push(Context.DOCUMENT);
		expectMethod(Method.ON_BLOCKS_BEGIN);
		handler.onDocumentBegin();

	}

	@Override
	public void onBlocksBegin() {
		isExpectedMethod(Method.ON_BLOCKS_BEGIN);
		expectMethod(Method.ON_BLOCK_BEGIN, Method.ON_BLOCKS_END);
		handler.onBlocksBegin();
	}

	@Override
	public void onBlockBegin(MarkdomBlockType type) {
		isExpectedMethod(Method.ON_BLOCK_BEGIN);
		checkParameter("block type", type, true);
		expectMethod(blockBeginCallback(type));
		handler.onBlockBegin(type);
	}

	private Method blockBeginCallback(MarkdomBlockType type) {
		switch (type) {
			case CODE:
				return Method.ON_CODE_BLOCK;
			case COMMENT:
				return Method.ON_COMMENT_BLOCK;
			case DIVISION:
				return Method.ON_DIVISION_BLOCK;
			case HEADING:
				return Method.ON_HEADING_BLOCK_BEGIN;
			case ORDERED_LIST:
				return Method.ON_ORDERED_LIST_BLOCK_BEGIN;
			case PARAGRAPH:
				return Method.ON_PARAGRAPH_BLOCK_BEGIN;
			case QUOTE:
				return Method.ON_QUOTE_BLOCK_BEGIN;
			case UNORDERED_LIST:
				return Method.ON_UNORDERED_LIST_BLOCK_BEGIN;
		}
		throw new InternalError("Unexpected block type: " + type);
	}

	@Override
	public void onBlockEnd(MarkdomBlockType type) {
		isExpectedMethod(Method.ON_BLOCK_END);
		checkAndCompareParameter("block type", type);
		expectMethod(Method.ON_NEXT_BLOCK, Method.ON_BLOCKS_END);
		handler.onBlockEnd(type);
	}

	@Override
	public void onNextBlock() {
		isExpectedMethod(Method.ON_NEXT_BLOCK);
		expectMethod(Method.ON_BLOCK_BEGIN);
		handler.onNextBlock();
	}

	@Override
	public void onBlocksEnd() {
		isExpectedMethod(Method.ON_BLOCKS_END);
		expectMethod(blocksEndCallback(contexts.peek()));
		handler.onBlocksEnd();
	}

	private Method blocksEndCallback(Context context) {
		switch (context) {
			case DOCUMENT:
				return Method.ON_DOCUMENT_END;
			case LIST_ITEM:
				return Method.ON_LIST_ITEM_END;
			case QUOTE_BLOCK:
				return Method.ON_QUOTE_BLOCK_END;
			default:
		}
		throw new InternalError("Unexpected blocks end context: " + context);
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
		isExpectedMethod(Method.ON_CODE_BLOCK);
		checkParameter("code", code, false);
		checkParameter("optional hint", hint, false);
		validateNoLineBreak(hint, "hint");
		expectMethod(Method.ON_BLOCK_END);
		handler.onCodeBlock(code, hint);
	}

	@Override
	public void onCommentBlock(String comment) {
		isExpectedMethod(Method.ON_COMMENT_BLOCK);
		checkParameter("comment", comment, false);
		expectMethod(Method.ON_BLOCK_END);
		handler.onCommentBlock(comment);
	}

	@Override
	public void onDivisionBlock() {
		isExpectedMethod(Method.ON_DIVISION_BLOCK);
		expectMethod(Method.ON_BLOCK_END);
		handler.onDivisionBlock();
	}

	@Override
	public void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		isExpectedMethod(Method.ON_HEADING_BLOCK_BEGIN);
		checkParameter("heading level", level, true);
		contexts.push(Context.HEADING_BLOCK);
		expectMethod(Method.ON_CONTENTS_BEGIN);
		handler.onHeadingBlockBegin(level);
	}

	@Override
	public void onHeadingBlockEnd(MarkdomHeadingLevel level) {
		isExpectedMethod(Method.ON_HEADING_BLOCK_END);
		checkAndCompareParameter("heading level", level);
		contexts.pop();
		expectMethod(Method.ON_BLOCK_END);
		handler.onHeadingBlockEnd(level);
	}

	@Override
	public void onOrderedListBlockBegin(Integer startIndex) {
		isExpectedMethod(Method.ON_ORDERED_LIST_BLOCK_BEGIN);
		checkParameter("start index", startIndex, true);
		validateNotNegative(startIndex);
		contexts.push(Context.ORDERED_LIST);
		expectMethod(Method.ON_LIST_ITEMS_BEGIN);
		handler.onOrderedListBlockBegin(startIndex);
	}

	@Override
	public void onOrderedListBlockEnd(Integer startIndex) {
		isExpectedMethod(Method.ON_ORDERED_LIST_BLOCK_END);
		checkAndCompareParameter("start index", startIndex);
		contexts.pop();
		expectMethod(Method.ON_BLOCK_END);
		handler.onOrderedListBlockEnd(startIndex);
	}

	@Override
	public void onParagraphBlockBegin() {
		isExpectedMethod(Method.ON_PARAGRAPH_BLOCK_BEGIN);
		contexts.push(Context.PARAGRAPH_BLOCK);
		expectMethod(Method.ON_CONTENTS_BEGIN);
		handler.onParagraphBlockBegin();
	}

	@Override
	public void onParagraphBlockEnd() {
		isExpectedMethod(Method.ON_PARAGRAPH_BLOCK_END);
		contexts.pop();
		expectMethod(Method.ON_BLOCK_END);
		handler.onParagraphBlockEnd();
	}

	@Override
	public void onQuoteBlockBegin() {
		isExpectedMethod(Method.ON_QUOTE_BLOCK_BEGIN);
		contexts.push(Context.QUOTE_BLOCK);
		expectMethod(Method.ON_BLOCKS_BEGIN);
		handler.onQuoteBlockBegin();
	}

	@Override
	public void onQuoteBlockEnd() {
		isExpectedMethod(Method.ON_QUOTE_BLOCK_END);
		contexts.pop();
		expectMethod(Method.ON_BLOCK_END);
		handler.onQuoteBlockEnd();
	}

	@Override
	public void onUnorderedListBlockBegin() {
		isExpectedMethod(Method.ON_UNORDERED_LIST_BLOCK_BEGIN);
		contexts.push(Context.UNORDERED_LIST);
		expectMethod(Method.ON_LIST_ITEMS_BEGIN);
		handler.onUnorderedListBlockBegin();
	}

	@Override
	public void onUnorderedListBlockEnd() {
		isExpectedMethod(Method.ON_UNORDERED_LIST_BLOCK_END);
		contexts.pop();
		expectMethod(Method.ON_BLOCK_END);
		handler.onUnorderedListBlockEnd();
	}

	@Override
	public void onListItemsBegin() {
		isExpectedMethod(Method.ON_LIST_ITEMS_BEGIN);
		expectMethod(Method.ON_LIST_ITEM_BEGIN, Method.ON_LIST_ITEMS_END);
		handler.onListItemsBegin();
	}

	@Override
	public void onListItemBegin() {
		isExpectedMethod(Method.ON_LIST_ITEM_BEGIN);
		contexts.push(Context.LIST_ITEM);
		expectMethod(Method.ON_BLOCKS_BEGIN);
		handler.onListItemBegin();
	}

	@Override
	public void onListItemEnd() {
		isExpectedMethod(Method.ON_LIST_ITEM_END);
		contexts.pop();
		expectMethod(Method.ON_NEXT_LIST_ITEM, Method.ON_LIST_ITEMS_END);
		handler.onListItemEnd();
	}

	@Override
	public void onNextListItem() {
		isExpectedMethod(Method.ON_NEXT_LIST_ITEM);
		expectMethod(Method.ON_LIST_ITEM_BEGIN);
		handler.onNextListItem();
	}

	@Override
	public void onListItemsEnd() {
		isExpectedMethod(Method.ON_LIST_ITEMS_END);
		expectMethod(listItemEndCallback(contexts.peek()));
		handler.onListItemsEnd();
	}

	private Method listItemEndCallback(Context context) {
		switch (context) {
			case ORDERED_LIST:
				return Method.ON_ORDERED_LIST_BLOCK_END;
			case UNORDERED_LIST:
				return Method.ON_UNORDERED_LIST_BLOCK_END;
			default:
		}
		throw new InternalError("Unexpected imtem end context: " + context);
	}

	@Override
	public void onContentsBegin() {
		isExpectedMethod(Method.ON_CONTENTS_BEGIN);
		expectMethod(Method.ON_CONTENT_BEGIN, Method.ON_CONTENTS_END);
		handler.onContentsBegin();
	}

	@Override
	public void onContentBegin(MarkdomContentType type) {
		isExpectedMethod(Method.ON_CONTENT_BEGIN);
		checkParameter("content type", type, true);
		expectMethod(contentBeginCallback(type));
		handler.onContentBegin(type);
	}

	private Method contentBeginCallback(MarkdomContentType type) {
		switch (type) {
			case CODE:
				return Method.ON_CODE_CONTENT;
			case EMPHASIS:
				return Method.ON_EMPHASIS_CONTENT_BEGIN;
			case IMAGE:
				return Method.ON_IMAGE_CONTENT;
			case LINE_BREAK:
				return Method.ON_LINE_BREAK_CONTENT;
			case LINK:
				return Method.ON_LINK_CONTENT_BEGIN;
			case TEXT:
				return Method.ON_TEXT_CONTENT;
		}
		throw new InternalError("Unexpected content type: " + type);
	}

	@Override
	public void onContentEnd(MarkdomContentType type) {
		isExpectedMethod(Method.ON_CONTENT_END);
		checkAndCompareParameter("content type", type);
		expectMethod(Method.ON_NEXT_CONTENT, Method.ON_CONTENTS_END);
		handler.onContentEnd(type);
	}

	@Override
	public void onNextContent() {
		isExpectedMethod(Method.ON_NEXT_CONTENT);
		expectMethod(Method.ON_CONTENT_BEGIN);
		handler.onNextContent();
	}

	@Override
	public void onContentsEnd() {
		isExpectedMethod(Method.ON_CONTENTS_END);
		expectMethod(contentsEndCallback(contexts.peek()));
		handler.onContentsEnd();
	}

	private Method contentsEndCallback(Context context) {
		switch (context) {
			case HEADING_BLOCK:
				return Method.ON_HEADING_BLOCK_END;
			case PARAGRAPH_BLOCK:
				return Method.ON_PARAGRAPH_BLOCK_END;
			case EMPHASIS_CONTENT:
				return Method.ON_EMPHASIS_CONTENT_END;
			case LINK_CONTENT:
				return Method.ON_LINK_CONTENT_END;
			default:
		}
		throw new InternalError("Unexpected contents end context: " + context);
	}

	@Override
	public void onCodeContent(String code) {
		isExpectedMethod(Method.ON_CODE_CONTENT);
		checkParameter("code", code, false);
		validateNoLineBreak(code, "code");
		expectMethod(Method.ON_CONTENT_END);
		handler.onCodeContent(code);
	}

	@Override
	public void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		isExpectedMethod(Method.ON_EMPHASIS_CONTENT_BEGIN);
		checkParameter("emphasis level", level, true);
		contexts.push(Context.EMPHASIS_CONTENT);
		expectMethod(Method.ON_CONTENTS_BEGIN);
		handler.onEmphasisContentBegin(level);
	}

	@Override
	public void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
		isExpectedMethod(Method.ON_EMPHASIS_CONTENT_END);
		checkAndCompareParameter("emphasis level", level);
		contexts.pop();
		expectMethod(Method.ON_CONTENT_END);
		handler.onEmphasisContentEnd(level);
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		isExpectedMethod(Method.ON_IMAGE_CONTENT);
		checkParameter("uri", uri, false);
		checkParameter("optional title", title, false);
		checkParameter("optional alternative", title, false);
		validateValidUri(uri);
		validateNoLineBreak(title, "title");
		validateNoLineBreak(alternative, "alternative");
		expectMethod(Method.ON_CONTENT_END);
		handler.onImageContent(uri, title, alternative);
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		isExpectedMethod(Method.ON_LINE_BREAK_CONTENT);
		checkParameter("hard", hard, false);
		checkLineBreakContext();
		expectMethod(Method.ON_CONTENT_END);
		handler.onLineBreakContent(hard);
	}

	@Override
	public void onLinkContentBegin(String uri, Optional<String> title) {
		isExpectedMethod(Method.ON_LINK_CONTENT_BEGIN);
		checkParameter("uri", uri, true);
		checkParameter("optional title", title, true);
		validateValidUri(uri);
		validateNoLineBreak(title, "title");
		checkLinkContentContext();
		contexts.push(Context.LINK_CONTENT);
		expectMethod(Method.ON_CONTENTS_BEGIN);
		handler.onLinkContentBegin(uri, title);
	}

	@Override
	public void onLinkContentEnd(String uri, Optional<String> title) {
		isExpectedMethod(Method.ON_LINK_CONTENT_END);
		checkAndCompareParameter("optional title", title);
		checkAndCompareParameter("uri", uri);
		contexts.pop();
		expectMethod(Method.ON_CONTENT_END);
		handler.onLinkContentEnd(uri, title);
	}

	@Override
	public void onTextContent(String text) {
		isExpectedMethod(Method.ON_TEXT_CONTENT);
		checkParameter("text", text, false);
		validateNoLineBreak(text, "text");
		expectMethod(Method.ON_CONTENT_END);
		handler.onTextContent(text);
	}

	@Override
	public void onDocumentEnd() {
		isExpectedMethod(Method.ON_DOCUMENT_END);
		expectMethod(Method.ON_RESULT);
		handler.onDocumentEnd();
	}

	@Override
	public Result getResult() {
		expectMethod(Method.ON_RESULT);
		return handler.getResult();
	}

	private void expectMethod(Method... methods) {
		expectedMethods.clear();
		for (Method method : methods) {
			expectedMethods.add(method);
		}
	}

	private void isExpectedMethod(Method callback) {
		if (!expectedMethods.contains(callback)) {
			throw new MarkdomException("The invoked method is none of " + expectedMethods + " in context " + parameters + ": " + callback);
		}
	}

	private void checkParameter(String name, Object value, boolean compareLater) {
		if (null == value) {
			throw new MarkdomException("The given " + name + " is null");
		}
		if (compareLater) {
			parameters.push(new Parameter<>(value));
		}
	}

	private void checkAndCompareParameter(String name, Object value) {
		if (null == value) {
			throw new MarkdomException("The given " + name + " is null");
		}
		Parameter<?> parameter = parameters.pop();
		if (!value.equals(parameter.payload)) {
			throw new MarkdomException("The given " + name + " is not " + parameter.payload + ": " + value);
		}
	}

	private void validateNotNegative(Integer startIndex) {
		if (startIndex < 0) {
			throw new MarkdomException("The given start index is negative: " + startIndex);
		}
	}

	private void validateNoLineBreak(Optional<String> string, String name) {
		if (string.isPresent()) {
			validateNoLineBreak(string.get(), name);
		}
	}

	private void validateNoLineBreak(String string, String name) {
		for (int i = 0, n = string.length(); i < n; i++) {
			if ('\n' == string.charAt(i)) {
				throw new MarkdomException("The given " + name + " contains a line break at index " + i + ": " + string);
			}
		}
	}

	private void validateValidUri(String uri) {
		try {
			new URI(uri);
		} catch (URISyntaxException e) {
			throw new MarkdomException("The given uri is invalid: " + uri, e);
		}
	}

	private void checkLineBreakContext() {
		if (contexts.contains(Context.HEADING_BLOCK)) {
			throw new MarkdomException("A line break appeared inside a heading block");
		}
	}

	private void checkLinkContentContext() {
		if (contexts.contains(Context.LINK_CONTENT)) {
			throw new MarkdomException("A link content appeared inside of another link content");
		}
	}

}
