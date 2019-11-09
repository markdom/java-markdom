/*
 * Copyright (c) 2016 Torsten Krause, Markenwerk GmbH
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.markdom.handler;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;

public final class LoggingMarkdomHandler<Result> implements MarkdomHandler<Result> {

	private final Appendable appendable;

	private final MarkdomHandler<Result> handler;

	private int depth;

	public LoggingMarkdomHandler(Appendable appendable) {
		this(appendable, new AbstractMarkdomHandler<Result>() {
		});
	}

	public LoggingMarkdomHandler(Appendable appendable, MarkdomHandler<Result> handler) {
		if (null == appendable) {
			throw new IllegalArgumentException("The given appendable is null");
		}
		if (null == handler) {
			throw new IllegalArgumentException("The given handler is null");
		}
		this.appendable = appendable;
		this.handler = handler;
	}

	@Override
	public void onDocumentBegin() {
		appendOpeningLine("onDocumentBegin()");
		handler.onDocumentBegin();
	}

	@Override
	public void onDocumentEnd() {
		appendClosingLine("onDocumentEnd()");
		handler.onDocumentEnd();
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
	public void onUnorderedListBlockBegin() {
		appendOpeningLine("onUnorderedListBlockBegin()");
		handler.onUnorderedListBlockBegin();
	}

	@Override
	public void onOrderedListBlockBegin(Integer startIndex) {
		appendOpeningLine("onOrderedListBlockBegin(" + startIndex + ")");
		handler.onOrderedListBlockBegin(startIndex);
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
	public void onUnorderedListBlockEnd() {
		appendClosingLine("onUnorderedListBlockEnd()");
		handler.onUnorderedListBlockEnd();
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

	private String name(Enum<?> value) {
		return null == value ? "null" : value.name();
	}

	private String quote(Optional<?> optional) {
		return null == optional ? "null" : (optional.isPresent() ? quote(optional.get()) : "empty");
	}

	private String quote(Object object) {
		return null == object ? "null" : ("\"" + object.toString().replaceAll(Pattern.quote("\n"), "\\\\n") + "\"");
	}

	private void appendOpeningLine(String line) {
		try {
			appendable.append(indentation(depth));
			appendable.append(line);
			appendable.append("\n");
			depth++;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void appendLeafLine(String line) {
		appendLeafLine(line, 0);
	}

	private void appendLeafLine(String line, int offset) {
		try {
			appendable.append(indentation(Math.max(0, depth + offset)));
			appendable.append(line);
			appendable.append("\n");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void appendClosingLine(String line) {
		try {
			depth--;
			appendable.append(indentation(depth));
			appendable.append(line);
			appendable.append("\n");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String indentation(int depth) {
		return new String(new char[2 * depth]).replace("\0", " ");
	}

	@Override
	public Result getResult() {
		return handler.getResult();
	}

}