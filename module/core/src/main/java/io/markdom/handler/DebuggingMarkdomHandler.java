package io.markdom.handler;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomException;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.ObjectHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.markenwerk.commons.iterables.ConvertingIterable;

public final class DebuggingMarkdomHandler<Result> implements MarkdomHandler<Result> {

	private static enum Context {

		DOCUMENT,

		HEADING_BLOCK,

		PARAGRAPH_BLOCK,

		QUOTE_BLOCK,

		ORDERED_LIST_BLOCK,

		UNORDERED_LIST_BLOCK,

		LIST_ITEM,

		EMPHASIS_CONTENT,

		LINK_CONTENT,

	}

	@AllArgsConstructor
	private static enum Method {

		ON_DOCUMENT_BEGIN("onDocumentBegin()"),

		ON_DOCUMENT_END("onDocumentEnd()"),

		ON_BLOCKS_BEGIN("onBlocksBegin()"),

		ON_BLOCK_BEGIN("onBlockBegin(MarkdomBlockType)"),

		ON_BLOCK_END("onBlockEnd(MarkdomBlockType)"),

		ON_NEXT_BLOCK("onNextBlock()"),

		ON_BLOCKS_END("onBlocksEnd()"),

		ON_CODE_BLOCK("onCodeBlock(String, Optional<String>)"),

		ON_COMMENT_BLOCK("onCommentBlock(String)"),

		ON_DIVISION_BLOCK("onDivisionBlock()"),

		ON_HEADING_BLOCK_BEGIN("onHeadingBlock(MarkdomHeadingLevel)"),

		ON_HEADING_BLOCK_END("onHeadingBlockEnd(MarkdomHeadingLevel)"),

		ON_ORDERED_LIST_BLOCK_BEGIN("onOrderedListBlockBegin(Integer)"),

		ON_ORDERED_LIST_BLOCK_END("onOrderedListBlockEnd(Integer)"),

		ON_PARAGRAPH_BLOCK_BEGIN("onParagraphBlockBegin()"),

		ON_PARAGRAPH_BLOCK_END("onParagraphBlockEnd()"),

		ON_QUOTE_BLOCK_BEGIN("onQuoteBlockBegin()"),

		ON_QUOTE_BLOCK_END("onQuoteBlockEnd()"),

		ON_UNORDERED_LIST_BLOCK_BEGIN("onUnorderedListBlockBegin()"),

		ON_UNORDERED_LIST_BLOCK_END("onUnorderedListBlockEnd()"),

		ON_LIST_ITEMS_BEGIN("onListItemsBegin()"),

		ON_LIST_ITEM_BEGIN("onListItemBegin()"),

		ON_LIST_ITEM_END("onListItemEnd()"),

		ON_NEXT_LIST_ITEM("onNextListItem()"),

		ON_LIST_ITEMS_END("onListItemsEnd()"),

		ON_CONTENTS_BEGIN("onContentsBegin()"),

		ON_CONTENT_BEGIN("onContentBegin()"),

		ON_CONTENT_END("onContentEnd()"),

		ON_NEXT_CONTENT("onNExtContent()"),

		ON_CONTENTS_END("onContentsEnd()"),

		ON_CODE_CONTENT("onCodeContent(String)"),

		ON_EMPHASIS_CONTENT_BEGIN("onEmphasisContentBegin(MarkdomEmphasisLevel)"),

		ON_EMPHASIS_CONTENT_END("onEmphasisContentEnd(markdomEmphasisLevel)"),

		ON_IMAGE_CONTENT("onImageContent(String, Optional<String>, Oprional<String>)"),

		ON_LINE_BREAK_CONTENT("onLineBreakContent(Boolean)"),

		ON_LINK_CONTENT_BEGIN("onLinkContentBegin(String, Optional<String)"),

		ON_LINK_CONTENT_END("onLinkContentEnd(String, Optional<String>)"),

		ON_TEXT_CONTENT("onTextContent(String)"),

		ON_RESULT("onResult()");

		@Getter
		private final String signature;

	}

	private final MarkdomAudit audit = new ParameterMarkdomAudit(violation -> {
		throw new MarkdomException(violation);
	});

	private final MarkdomHandler<Result> handler;

	private final Stack<Context> contexts = new Stack<Context>();

	private final Stack<Queue<Object>> parameterFrames = new Stack<>();

	private final EnumSet<Method> expectedMethods = EnumSet.noneOf(Method.class);

	public DebuggingMarkdomHandler(MarkdomHandler<Result> handler) {
		this.handler = new AuditingMarkdomHandler<>(ObjectHelper.notNull("handler", handler), audit);
		expectMethods(Method.ON_DOCUMENT_BEGIN);
	}

	@Override
	public void onDocumentBegin() {
		isExpectedMethod(Method.ON_DOCUMENT_BEGIN);
		expectMethods(Method.ON_BLOCKS_BEGIN);
		openContext(Context.DOCUMENT);
		handler.onDocumentBegin();

	}

	@Override
	public void onBlocksBegin() {
		isExpectedMethod(Method.ON_BLOCKS_BEGIN);
		expectMethods(Method.ON_BLOCK_BEGIN, Method.ON_BLOCKS_END);
		handler.onBlocksBegin();
	}

	@Override
	public void onBlockBegin(MarkdomBlockType type) {
		isExpectedMethod(Method.ON_BLOCK_BEGIN);
		expectMethods(blockBeginCallback(type));
		openParameterFrame();
		handler.onBlockBegin(storeParameter(type));
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
		expectMethods(Method.ON_NEXT_BLOCK, Method.ON_BLOCKS_END);
		handler.onBlockEnd(restoreParameter("type", type));
		closeParameterFrame();
	}

	@Override
	public void onNextBlock() {
		isExpectedMethod(Method.ON_NEXT_BLOCK);
		expectMethods(Method.ON_BLOCK_BEGIN);
		handler.onNextBlock();
	}

	@Override
	public void onBlocksEnd() {
		isExpectedMethod(Method.ON_BLOCKS_END);
		expectMethods(blocksEndCallback(contexts.peek()));
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
		expectMethods(Method.ON_BLOCK_END);
		handler.onCodeBlock(code, hint);
	}

	@Override
	public void onCommentBlock(String comment) {
		isExpectedMethod(Method.ON_COMMENT_BLOCK);
		expectMethods(Method.ON_BLOCK_END);
		handler.onCommentBlock(comment);
	}

	@Override
	public void onDivisionBlock() {
		isExpectedMethod(Method.ON_DIVISION_BLOCK);
		expectMethods(Method.ON_BLOCK_END);
		handler.onDivisionBlock();
	}

	@Override
	public void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		isExpectedMethod(Method.ON_HEADING_BLOCK_BEGIN);
		expectMethods(Method.ON_CONTENTS_BEGIN);
		openParameterFrame();
		openContext(Context.HEADING_BLOCK);
		handler.onHeadingBlockBegin(storeParameter(level));
	}

	@Override
	public void onHeadingBlockEnd(MarkdomHeadingLevel level) {
		isExpectedMethod(Method.ON_HEADING_BLOCK_END);
		expectMethods(Method.ON_BLOCK_END);
		handler.onHeadingBlockEnd(restoreParameter("level", level));
		closeParameterFrame();
		closeContext();
	}

	@Override
	public void onOrderedListBlockBegin(Integer startIndex) {
		isExpectedMethod(Method.ON_ORDERED_LIST_BLOCK_BEGIN);
		expectMethods(Method.ON_LIST_ITEMS_BEGIN);
		openParameterFrame();
		openContext(Context.ORDERED_LIST_BLOCK);
		handler.onOrderedListBlockBegin(storeParameter(startIndex));
	}

	@Override
	public void onOrderedListBlockEnd(Integer startIndex) {
		isExpectedMethod(Method.ON_ORDERED_LIST_BLOCK_END);
		expectMethods(Method.ON_BLOCK_END);
		handler.onOrderedListBlockEnd(restoreParameter("start index", startIndex));
		closeParameterFrame();
		closeContext();
	}

	@Override
	public void onParagraphBlockBegin() {
		isExpectedMethod(Method.ON_PARAGRAPH_BLOCK_BEGIN);
		expectMethods(Method.ON_CONTENTS_BEGIN);
		openContext(Context.PARAGRAPH_BLOCK);
		handler.onParagraphBlockBegin();
	}

	@Override
	public void onParagraphBlockEnd() {
		isExpectedMethod(Method.ON_PARAGRAPH_BLOCK_END);
		expectMethods(Method.ON_BLOCK_END);
		handler.onParagraphBlockEnd();
		closeContext();
	}

	@Override
	public void onQuoteBlockBegin() {
		openContext(Context.QUOTE_BLOCK);
		isExpectedMethod(Method.ON_QUOTE_BLOCK_BEGIN);
		expectMethods(Method.ON_BLOCKS_BEGIN);
		handler.onQuoteBlockBegin();
	}

	@Override
	public void onQuoteBlockEnd() {
		isExpectedMethod(Method.ON_QUOTE_BLOCK_END);
		expectMethods(Method.ON_BLOCK_END);
		handler.onQuoteBlockEnd();
		closeContext();
	}

	@Override
	public void onUnorderedListBlockBegin() {
		openContext(Context.UNORDERED_LIST_BLOCK);
		isExpectedMethod(Method.ON_UNORDERED_LIST_BLOCK_BEGIN);
		expectMethods(Method.ON_LIST_ITEMS_BEGIN);
		handler.onUnorderedListBlockBegin();
	}

	@Override
	public void onUnorderedListBlockEnd() {
		isExpectedMethod(Method.ON_UNORDERED_LIST_BLOCK_END);
		expectMethods(Method.ON_BLOCK_END);
		handler.onUnorderedListBlockEnd();
		closeContext();
	}

	@Override
	public void onListItemsBegin() {
		isExpectedMethod(Method.ON_LIST_ITEMS_BEGIN);
		expectMethods(Method.ON_LIST_ITEM_BEGIN, Method.ON_LIST_ITEMS_END);
		handler.onListItemsBegin();
	}

	@Override
	public void onListItemBegin() {
		openContext(Context.LIST_ITEM);
		isExpectedMethod(Method.ON_LIST_ITEM_BEGIN);
		expectMethods(Method.ON_BLOCKS_BEGIN);
		handler.onListItemBegin();
	}

	@Override
	public void onListItemEnd() {
		isExpectedMethod(Method.ON_LIST_ITEM_END);
		expectMethods(Method.ON_NEXT_LIST_ITEM, Method.ON_LIST_ITEMS_END);
		handler.onListItemEnd();
		closeContext();
	}

	@Override
	public void onNextListItem() {
		isExpectedMethod(Method.ON_NEXT_LIST_ITEM);
		expectMethods(Method.ON_LIST_ITEM_BEGIN);
		handler.onNextListItem();
	}

	@Override
	public void onListItemsEnd() {
		isExpectedMethod(Method.ON_LIST_ITEMS_END);
		expectMethods(listItemEndCallback(contexts.peek()));
		handler.onListItemsEnd();
	}

	private Method listItemEndCallback(Context context) {
		switch (context) {
			case ORDERED_LIST_BLOCK:
				return Method.ON_ORDERED_LIST_BLOCK_END;
			case UNORDERED_LIST_BLOCK:
				return Method.ON_UNORDERED_LIST_BLOCK_END;
			default:
		}
		throw new InternalError("Unexpected imtem end context: " + context);
	}

	@Override
	public void onContentsBegin() {
		isExpectedMethod(Method.ON_CONTENTS_BEGIN);
		expectMethods(Method.ON_CONTENT_BEGIN, Method.ON_CONTENTS_END);
		handler.onContentsBegin();
	}

	@Override
	public void onContentBegin(MarkdomContentType type) {
		isExpectedMethod(Method.ON_CONTENT_BEGIN);
		expectMethods(contentBeginCallback(type));
		openParameterFrame();
		handler.onContentBegin(storeParameter(type));
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
		expectMethods(Method.ON_NEXT_CONTENT, Method.ON_CONTENTS_END);
		handler.onContentEnd(restoreParameter("type", type));
		closeParameterFrame();
	}

	@Override
	public void onNextContent() {
		isExpectedMethod(Method.ON_NEXT_CONTENT);
		expectMethods(Method.ON_CONTENT_BEGIN);
		handler.onNextContent();
	}

	@Override
	public void onContentsEnd() {
		isExpectedMethod(Method.ON_CONTENTS_END);
		expectMethods(contentsEndCallback(contexts.peek()));
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
		expectMethods(Method.ON_CONTENT_END);
		handler.onCodeContent(code);
	}

	@Override
	public void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		isExpectedMethod(Method.ON_EMPHASIS_CONTENT_BEGIN);
		expectMethods(Method.ON_CONTENTS_BEGIN);
		openParameterFrame();
		openContext(Context.EMPHASIS_CONTENT);
		handler.onEmphasisContentBegin(storeParameter(level));
	}

	@Override
	public void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
		isExpectedMethod(Method.ON_EMPHASIS_CONTENT_END);
		expectMethods(Method.ON_CONTENT_END);
		handler.onEmphasisContentEnd(restoreParameter("level", level));
		closeParameterFrame();
		closeContext();
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		isExpectedMethod(Method.ON_IMAGE_CONTENT);
		expectMethods(Method.ON_CONTENT_END);
		handler.onImageContent(uri, title, alternative);
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		isExpectedMethod(Method.ON_LINE_BREAK_CONTENT);
		expectMethods(Method.ON_CONTENT_END);
		checkLineBreakInContext();
		handler.onLineBreakContent(hard);
	}

	@Override
	public void onLinkContentBegin(String uri, Optional<String> title) {
		isExpectedMethod(Method.ON_LINK_CONTENT_BEGIN);
		expectMethods(Method.ON_CONTENTS_BEGIN);
		openParameterFrame();
		checkLinkContentInContext();
		openContext(Context.LINK_CONTENT);
		handler.onLinkContentBegin(storeParameter(uri), storeParameter(title));
	}

	@Override
	public void onLinkContentEnd(String uri, Optional<String> title) {
		isExpectedMethod(Method.ON_LINK_CONTENT_END);
		expectMethods(Method.ON_CONTENT_END);
		handler.onLinkContentEnd(restoreParameter("uri", uri), restoreParameter("title optional", title));
		closeParameterFrame();
		closeContext();
	}

	@Override
	public void onTextContent(String text) {
		isExpectedMethod(Method.ON_TEXT_CONTENT);
		expectMethods(Method.ON_CONTENT_END);
		handler.onTextContent(text);
	}

	@Override
	public void onDocumentEnd() {
		isExpectedMethod(Method.ON_DOCUMENT_END);
		expectMethods(Method.ON_RESULT);
		handler.onDocumentEnd();
	}

	@Override
	public Result getResult() {
		isExpectedMethod(Method.ON_RESULT);
		return handler.getResult();
	}

	private void openContext(Context context) {
		contexts.push(context);
	}

	private void checkLineBreakInContext() {
		if (contexts.contains(Context.HEADING_BLOCK)) {
			throw new MarkdomException("A line break appeared inside a heading block");
		}
	}

	private void checkLinkContentInContext() {
		if (contexts.contains(Context.LINK_CONTENT)) {
			throw new MarkdomException("A link content appeared inside of another link content");
		}
	}

	private void closeContext() {
		contexts.pop();
	}

	private void isExpectedMethod(Method method) {
		if (!expectedMethods.contains(method)) {
			throw new MarkdomException("The invoked method is none of " + expectedMethods() + ": " + method.getSignature());
		}
	}

	private String expectedMethods() {
		return String.join(", ", new ConvertingIterable<>(expectedMethods, Method::getSignature));
	}

	private void expectMethods(Method... methods) {
		expectedMethods.clear();
		for (Method method : methods) {
			expectedMethods.add(method);
		}
	}

	private void openParameterFrame() {
		parameterFrames.push(new LinkedList<>());
	}

	private <Value> Value storeParameter(Value value) {
		parameterFrames.peek().add(value);
		return value;
	}

	private <Value> Value restoreParameter(String name, Value value) {
		Object storedValue = parameterFrames.peek().remove();
		if (!Objects.equals(value, storedValue)) {
			throw new MarkdomException("The given " + name + " is not equal to " + storedValue + ": " + value);
		}
		return value;
	}

	private void closeParameterFrame() {
		parameterFrames.pop();
	}

}
