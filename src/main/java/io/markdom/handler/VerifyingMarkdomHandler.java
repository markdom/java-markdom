package io.markdom.handler;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Stack;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomException;
import io.markdom.common.MarkdomHeadingLevel;
import lombok.Data;

public final class VerifyingMarkdomHandler<Result> implements MarkdomHandler<Result> {

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

	public VerifyingMarkdomHandler(MarkdomHandler<Result> handler) {
		if (null == handler) {
			throw new IllegalArgumentException("The given Markdom handler is null");
		}
		this.handler = handler;
		expect(Method.ON_DOCUMENT_BEGIN);
	}

	@Override
	public void onDocumentBegin() {
		testExpectation(Method.ON_DOCUMENT_BEGIN);
		contexts.push(Context.DOCUMENT);
		expect(Method.ON_BLOCKS_BEGIN);
		handler.onDocumentBegin();

	}

	@Override
	public void onDocumentEnd() {
		testExpectation(Method.ON_DOCUMENT_END);
		expect(Method.ON_RESULT);
		handler.onDocumentEnd();
	}

	@Override
	public void onBlocksBegin() {
		testExpectation(Method.ON_BLOCKS_BEGIN);
		expect(Method.ON_BLOCK_BEGIN, Method.ON_BLOCKS_END);
		handler.onBlocksBegin();
	}

	@Override
	public void onBlockBegin(MarkdomBlockType type) {
		testExpectation(Method.ON_BLOCK_BEGIN);
		if (null == type) {
			throw new MarkdomException("The given block type is null");
		}
		parameters.push(new Parameter<MarkdomBlockType>(type));
		expect(blockBeginCallback(type));
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
		throw new AssertionError("Unexpected block type: " + type);
	}

	@Override
	public void onBlockEnd(MarkdomBlockType type) {
		testExpectation(Method.ON_BLOCK_END);
		testParameter(type, "block type");
		expect(Method.ON_NEXT_BLOCK, Method.ON_BLOCKS_END);
		handler.onBlockEnd(type);
	}

	@Override
	public void onNextBlock() {
		testExpectation(Method.ON_NEXT_BLOCK);
		expect(Method.ON_BLOCK_BEGIN);
		handler.onNextBlock();
	}

	@Override
	public void onBlocksEnd() {
		testExpectation(Method.ON_BLOCKS_END);
		expect(blocksEndCallback(contexts.firstElement()));
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
		throw new AssertionError("Unexpected blocks end context: " + context);
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
		testExpectation(Method.ON_CODE_BLOCK);
		if (null == code) {
			throw new MarkdomException("The given code is null");
		}
		expect(Method.ON_BLOCK_END);
		handler.onCodeBlock(code, hint);
	}

	@Override
	public void onCommentBlock(String comment) {
		testExpectation(Method.ON_COMMENT_BLOCK);
		if (null == comment) {
			throw new MarkdomException("The given comment is null");
		}
		expect(Method.ON_BLOCK_END);
		handler.onCommentBlock(comment);
	}

	@Override
	public void onDivisionBlock() {
		testExpectation(Method.ON_DIVISION_BLOCK);
		expect(Method.ON_BLOCK_END);
		handler.onDivisionBlock();
	}

	@Override
	public void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		testExpectation(Method.ON_HEADING_BLOCK_BEGIN);
		if (null == level) {
			throw new MarkdomException("The given heading level is null");
		}
		parameters.push(new Parameter<MarkdomHeadingLevel>(level));
		contexts.push(Context.HEADING_BLOCK);
		expect(Method.ON_CONTENTS_BEGIN);
		handler.onHeadingBlockBegin(level);
	}

	@Override
	public void onHeadingBlockEnd(MarkdomHeadingLevel level) {
		testExpectation(Method.ON_HEADING_BLOCK_END);
		testParameter(level, "heading level");
		contexts.pop();
		expect(Method.ON_BLOCK_END);
		handler.onHeadingBlockEnd(level);
	}

	@Override
	public void onOrderedListBlockBegin(Integer startIndex) {
		testExpectation(Method.ON_ORDERED_LIST_BLOCK_BEGIN);
		parameters.push(new Parameter<Integer>(startIndex));
		contexts.push(Context.ORDERED_LIST);
		expect(Method.ON_LIST_ITEMS_BEGIN);
		handler.onOrderedListBlockBegin(startIndex);
	}

	@Override
	public void onOrderedListBlockEnd(Integer startIndex) {
		testExpectation(Method.ON_ORDERED_LIST_BLOCK_END);
		testParameter(startIndex, "start index");
		contexts.pop();
		expect(Method.ON_BLOCK_END);
		handler.onOrderedListBlockEnd(startIndex);
	}

	@Override
	public void onParagraphBlockBegin() {
		testExpectation(Method.ON_PARAGRAPH_BLOCK_BEGIN);
		contexts.push(Context.PARAGRAPH_BLOCK);
		expect(Method.ON_CONTENTS_BEGIN);
		handler.onParagraphBlockBegin();
	}

	@Override
	public void onParagraphBlockEnd() {
		testExpectation(Method.ON_PARAGRAPH_BLOCK_END);
		contexts.pop();
		expect(Method.ON_BLOCK_END);
		handler.onParagraphBlockEnd();
	}

	@Override
	public void onQuoteBlockBegin() {
		testExpectation(Method.ON_QUOTE_BLOCK_BEGIN);
		contexts.push(Context.QUOTE_BLOCK);
		expect(Method.ON_BLOCKS_BEGIN);
		handler.onQuoteBlockBegin();
	}

	@Override
	public void onQuoteBlockEnd() {
		testExpectation(Method.ON_QUOTE_BLOCK_END);
		contexts.pop();
		expect(Method.ON_BLOCK_END);
		handler.onQuoteBlockEnd();
	}

	@Override
	public void onUnorderedListBlockBegin() {
		testExpectation(Method.ON_UNORDERED_LIST_BLOCK_BEGIN);
		contexts.push(Context.UNORDERED_LIST);
		expect(Method.ON_LIST_ITEMS_BEGIN);
		handler.onUnorderedListBlockBegin();
	}

	@Override
	public void onUnorderedListBlockEnd() {
		testExpectation(Method.ON_UNORDERED_LIST_BLOCK_END);
		contexts.pop();
		expect(Method.ON_BLOCK_END);
		handler.onUnorderedListBlockEnd();
	}

	@Override
	public void onListItemsBegin() {
		testExpectation(Method.ON_LIST_ITEMS_BEGIN);
		expect(Method.ON_LIST_ITEM_BEGIN, Method.ON_LIST_ITEMS_END);
		handler.onListItemsBegin();
	}

	@Override
	public void onListItemBegin() {
		testExpectation(Method.ON_LIST_ITEM_BEGIN);
		contexts.push(Context.LIST_ITEM);
		expect(Method.ON_BLOCKS_BEGIN);
		handler.onListItemBegin();
	}

	@Override
	public void onListItemEnd() {
		testExpectation(Method.ON_LIST_ITEM_END);
		contexts.pop();
		expect(Method.ON_NEXT_LIST_ITEM, Method.ON_LIST_ITEMS_END);
		handler.onListItemEnd();
	}

	@Override
	public void onNextListItem() {
		testExpectation(Method.ON_NEXT_LIST_ITEM);
		expect(Method.ON_LIST_ITEM_BEGIN);
		handler.onNextListItem();
	}

	@Override
	public void onListItemsEnd() {
		testExpectation(Method.ON_LIST_ITEMS_END);
		expect(listItemEndCallback(contexts.firstElement()));
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
		throw new AssertionError("Unexpected imtem end context: " + context);
	}

	@Override
	public void onContentsBegin() {
		testExpectation(Method.ON_CONTENTS_BEGIN);
		expect(Method.ON_CONTENT_BEGIN, Method.ON_CONTENTS_END);
		handler.onContentsBegin();
	}

	@Override
	public void onContentBegin(MarkdomContentType type) {
		testExpectation(Method.ON_CONTENT_BEGIN);
		if (null == type) {
			throw new MarkdomException("The given content type is null");
		}
		parameters.push(new Parameter<MarkdomContentType>(type));
		expect(contentBeginCallback(type));
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
		throw new AssertionError("Unexpected content type: " + type);
	}

	@Override
	public void onContentEnd(MarkdomContentType type) {
		testExpectation(Method.ON_CONTENT_END);
		testParameter(type, "content type");
		expect(Method.ON_NEXT_CONTENT, Method.ON_CONTENTS_END);
		handler.onContentEnd(type);
	}

	@Override
	public void onNextContent() {
		testExpectation(Method.ON_NEXT_CONTENT);
		expect(Method.ON_CONTENT_BEGIN);
		handler.onNextContent();
	}

	@Override
	public void onContentsEnd() {
		testExpectation(Method.ON_CONTENTS_END);
		expect(contentsEndCallback(contexts.firstElement()));
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
		throw new AssertionError("Unexpected contents end context: " + context);
	}

	@Override
	public void onCodeContent(String code) {
		testExpectation(Method.ON_CODE_CONTENT);
		if (null == code) {
			throw new MarkdomException("The given code is null");
		}
		expect(Method.ON_CONTENT_END);
		handler.onCodeContent(code);
	}

	@Override
	public void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		testExpectation(Method.ON_EMPHASIS_CONTENT_BEGIN);
		if (null == level) {
			throw new MarkdomException("The given emphasis level is null");
		}
		parameters.push(new Parameter<MarkdomEmphasisLevel>(level));
		contexts.push(Context.EMPHASIS_CONTENT);
		expect(Method.ON_CONTENTS_BEGIN);
		handler.onEmphasisContentBegin(level);
	}

	@Override
	public void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
		testExpectation(Method.ON_EMPHASIS_CONTENT_END);
		testParameter(level, "emphasis level");
		contexts.pop();
		expect(Method.ON_CONTENT_END);
		handler.onEmphasisContentEnd(level);
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		testExpectation(Method.ON_IMAGE_CONTENT);
		if (null == uri) {
			throw new MarkdomException("The given URI is null");
		}
		expect(Method.ON_CONTENT_END);
		handler.onImageContent(uri, title, alternative);
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		testExpectation(Method.ON_LINE_BREAK_CONTENT);
		expect(Method.ON_CONTENT_END);
		handler.onLineBreakContent(hard);
	}

	@Override
	public void onLinkContentBegin(String uri, Optional<String> title) {
		testExpectation(Method.ON_LINK_CONTENT_BEGIN);
		if (null == uri) {
			throw new MarkdomException("The given URI is null");
		}
		parameters.push(new Parameter<String>(uri));
		contexts.push(Context.LINK_CONTENT);
		expect(Method.ON_CONTENTS_BEGIN);
		handler.onLinkContentBegin(uri, title);
	}

	@Override
	public void onLinkContentEnd(String uri, Optional<String> title) {
		testExpectation(Method.ON_LINK_CONTENT_END);
		testParameter(uri, "URI");
		contexts.pop();
		expect(Method.ON_CONTENT_END);
		handler.onLinkContentEnd(uri, title);
	}

	@Override
	public void onTextContent(String text) {
		testExpectation(Method.ON_TEXT_CONTENT);
		if (null == text) {
			throw new MarkdomException("The given text is null");
		}
		expect(Method.ON_CONTENT_END);
		handler.onTextContent(text);
	}

	@Override
	public Result getResult() {
		expect(Method.ON_RESULT);
		return handler.getResult();
	}

	private void expect(Method... callbacks) {
		expectedMethods.clear();
		for (Method callback : callbacks) {
			expectedMethods.add(callback);
		}
	}

	private void testExpectation(Method callback) {
		if (!expectedMethods.contains(callback)) {
			throw new MarkdomException(
				"The invoked callback method is none of " + expectedMethods + " in context " + parameters + ": " + callback);
		}
	}

	private void testParameter(Object value, String name) {
		if (null == value) {
			throw new MarkdomException("The given " + name + " is null");
		}
		Parameter<?> parameter = parameters.pop();
		if (!value.equals(parameter.payload)) {
			throw new MarkdomException("The given " + name + " is not " + parameter.payload + ": " + value);
		}
	}

}
