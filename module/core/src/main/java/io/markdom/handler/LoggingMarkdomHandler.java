package io.markdom.handler;

import java.util.Optional;
import java.util.regex.Pattern;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.ObjectHelper;
import lombok.SneakyThrows;

public final class LoggingMarkdomHandler<Result> implements MarkdomHandler<Result> {

	private final Appendable appendable;

	private final MarkdomHandler<Result> handler;

	private int depth;

	public LoggingMarkdomHandler(Appendable appendable) {
		this(appendable, new AbstractMarkdomHandler<Result>() {
		});
	}

	public LoggingMarkdomHandler(Appendable appendable, MarkdomHandler<Result> handler) {
		this.appendable = ObjectHelper.notNull("appendable", appendable);
		this.handler = ObjectHelper.notNull("handler", handler);
	}

	@Override
	public void onDocumentBegin() {
		appendOpeningLine("onDocumentBegin()");
		handler.onDocumentBegin();
	}

	@Override
	public void onBlocksBegin() {
		appendOpeningLine("onBlocksBegin()");
		handler.onBlocksBegin();
	}

	@Override
	public void onBlockBegin(MarkdomBlockType type) {
		appendLeafLine("onBlockBegin(" + name(type) + ")");
		handler.onBlockBegin(type);
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
		appendLeafLine("onCodeBlock(" + quote(code) + ", " + quote(hint) + ")");
		handler.onCodeBlock(code, hint);
	}

	@Override
	public void onCommentBlock(String comment) {
		appendLeafLine("onCommentBlock(" + quote(comment) + ")");
		handler.onCommentBlock(comment);
	}

	@Override
	public void onDivisionBlock() {
		appendLeafLine("onDivisionBlock()");
		handler.onDivisionBlock();
	}

	@Override
	public void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		appendOpeningLine("onHeadingBlockBegin(" + name(level) + ")");
		handler.onHeadingBlockBegin(level);
	}

	@Override
	public void onHeadingBlockEnd(MarkdomHeadingLevel level) {
		appendClosingLine("onHeadingBlockEnd(" + name(level) + ")");
		handler.onHeadingBlockEnd(level);
	}

	@Override
	public void onOrderedListBlockBegin(Integer startIndex) {
		appendOpeningLine("onOrderedListBlockBegin(" + startIndex + ")");
		handler.onOrderedListBlockBegin(startIndex);
	}

	@Override
	public void onOrderedListBlockEnd(Integer startIndex) {
		appendClosingLine("onOrderedListBlockEnd(" + startIndex + ")");
		handler.onOrderedListBlockEnd(startIndex);
	}

	@Override
	public void onParagraphBlockBegin() {
		appendOpeningLine("onParagraphBlockBegin()");
		handler.onParagraphBlockBegin();
	}

	@Override
	public void onParagraphBlockEnd() {
		appendClosingLine("onParagraphBlockEnd()");
		handler.onParagraphBlockEnd();
	}

	@Override
	public void onQuoteBlockBegin() {
		appendOpeningLine("onQuoteBlockBegin()");
		handler.onQuoteBlockBegin();
	}

	@Override
	public void onQuoteBlockEnd() {
		appendClosingLine("onQuoteBlockEnd()");
		handler.onQuoteBlockEnd();
	}

	@Override
	public void onUnorderedListBlockBegin() {
		appendOpeningLine("onUnorderedListBlockBegin()");
		handler.onUnorderedListBlockBegin();
	}

	@Override
	public void onUnorderedListBlockEnd() {
		appendClosingLine("onUnorderedListBlockEnd()");
		handler.onUnorderedListBlockEnd();
	}

	@Override
	public void onBlockEnd(MarkdomBlockType type) {
		appendLeafLine("onBlockEnd(" + name(type) + ")");
		handler.onBlockEnd(type);
	}

	@Override
	public void onNextBlock() {
		appendLeafLine("onNextBlock()", -1);
		handler.onNextBlock();
	}

	@Override
	public void onBlocksEnd() {
		appendClosingLine("onBlocksEnd()");
		handler.onBlocksEnd();
	}

	@Override
	public void onListItemsBegin() {
		appendOpeningLine("onListItemsBegin()");
		handler.onListItemsBegin();
	}

	@Override
	public void onListItemBegin() {
		appendOpeningLine("onListItemBegin()");
		handler.onListItemBegin();
	}

	@Override
	public void onListItemEnd() {
		appendClosingLine("onListItemEnd()");
		handler.onListItemEnd();
	}

	@Override
	public void onNextListItem() {
		appendLeafLine("onNextListItem()", -1);
		handler.onNextListItem();
	}

	@Override
	public void onListItemsEnd() {
		appendClosingLine("onListItemsEnd()");
		handler.onListItemsEnd();
	}

	@Override
	public void onContentsBegin() {
		appendOpeningLine("onContentsBegin()");
		handler.onContentsBegin();
	}

	@Override
	public void onContentBegin(MarkdomContentType type) {
		appendLeafLine("onContentBegin(" + name(type) + ")");
		handler.onContentBegin(type);
	}

	@Override
	public void onCodeContent(String code) {
		appendLeafLine("onCodeContent(" + quote(code) + ")");
		handler.onCodeContent(code);
	}

	@Override
	public void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		appendOpeningLine("onEmphasisContentBegin(" + name(level) + ")");
		handler.onEmphasisContentBegin(level);
	}

	@Override
	public void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
		appendClosingLine("onEmphasisContentEnd(" + name(level) + ")");
		handler.onEmphasisContentEnd(level);
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		appendLeafLine("onImageContent(" + quote(uri) + ", " + quote(title) + ", " + quote(alternative) + ")");
		handler.onImageContent(uri, title, alternative);
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		appendLeafLine("onLineBreakContent(" + hard + ")");
		handler.onLineBreakContent(hard);
	}

	@Override
	public void onLinkContentBegin(String uri, Optional<String> title) {
		appendOpeningLine("onLinkContentBegin(" + quote(uri) + ", " + quote(title) + ")");
		handler.onLinkContentBegin(uri, title);
	}

	@Override
	public void onLinkContentEnd(String uri, Optional<String> title) {
		appendClosingLine("onLinkContentEnd(" + quote(uri) + ", " + quote(title) + ")");
		handler.onLinkContentEnd(uri, title);
	}

	@Override
	public void onTextContent(String text) {
		appendLeafLine("onTextContent(" + quote(text) + ")");
		handler.onTextContent(text);
	}

	@Override
	public void onContentEnd(MarkdomContentType type) {
		appendLeafLine("onContentEnd(" + name(type) + ")");
		handler.onContentEnd(type);
	}

	@Override
	public void onNextContent() {
		appendLeafLine("onNextContent()", -1);
		handler.onNextContent();
	}

	@Override
	public void onContentsEnd() {
		appendClosingLine("onContentsEnd()");
		handler.onContentsEnd();
	}

	@Override
	public void onDocumentEnd() {
		appendClosingLine("onDocumentEnd()");
		handler.onDocumentEnd();
	}

	private String name(Enum<?> value) {
		return null == value ? "null" : value.name();
	}

	private String quote(Optional<?> optional) {
		return null == optional ? "null" : (optional.isPresent() ? quote(optional.get()) : "empty");
	}

	private String quote(Object object) {
		return null == object ? "null" : ("\"" + object.toString().replaceAll(Pattern.quote("\n"), "\\\\n") + "\"");
	}

	@SneakyThrows
	private void appendOpeningLine(String line) {
		appendable.append(indentation(depth));
		appendable.append(line);
		appendable.append("\n");
		depth++;
	}

	private void appendLeafLine(String line) {
		appendLeafLine(line, 0);
	}

	@SneakyThrows
	private void appendLeafLine(String line, int offset) {
		appendable.append(indentation(Math.max(0, depth + offset)));
		appendable.append(line);
		appendable.append("\n");
	}

	@SneakyThrows
	private void appendClosingLine(String line) {
		depth--;
		appendable.append(indentation(depth));
		appendable.append(line);
		appendable.append("\n");
	}

	private String indentation(int depth) {
		return new String(new char[2 * depth]).replace("\0", " ");
	}

	@Override
	public Result getResult() {
		return handler.getResult();
	}

}