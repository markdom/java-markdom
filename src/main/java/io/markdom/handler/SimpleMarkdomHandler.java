package io.markdom.handler;

import java.util.Optional;
import java.util.Stack;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;

/**
 * A {@link SimpleMarkdomHandler} takes an existing {@link MarkdomHandler} and provides a simplified interface.
 * 
 * A {@link SimpleMarkdomHandler} is intended as a utility for {@link MarkdomDispatcher}s and simplifies the handling of
 * a Markdom document
 * 
 * 
 * <ul>
 * <li>by automatically calling the methods for
 * <ul>
 * <li>sequences of blocks, list items and contents ({@link MarkdomHandler#onBlocksBegin() onBlocksBegin},
 * {@link MarkdomHandler#onNextBlock() onNextBlock}, {@link MarkdomHandler#onBlocksEnd() onBlocksEnd},
 * {@link MarkdomHandler#onListItemsBegin() onListItemsBegin}, {@link MarkdomHandler#onNextListItem() onNextListItem},
 * {@link MarkdomHandler#onListItemsEnd() onListItemsEnd}, {@link MarkdomHandler#onContentsBegin() onContentsBegin},
 * {@link MarkdomHandler#onNextContent() onNextContent}, {@link MarkdomHandler#onContentsEnd() onContentsEnd})</li>
 * <li>generic blocks, list items and contents ({@link MarkdomHandler#onBlockBegin(MarkdomBlockType) onBlockBegin},
 * {@link MarkdomHandler#onBlockEnd(MarkdomBlockType) onBlockEnd}, {@link MarkdomHandler#onListItemBegin()
 * onListItemBegin}, {@link MarkdomHandler#onListItemEnd() onListItemEnd},
 * {@link MarkdomHandler#onContentBegin(MarkdomContentType) onContentBegin},
 * {@link MarkdomHandler#onContentEnd(MarkdomContentType) onContentEnd})</li>
 * </ul>
 * </li>
 * <li>and by remembering and automatically adding the parameters for ending methods
 * ({@link MarkdomHandler#onHeadingBlockEnd(MarkdomHeadingLevel) onHeadingBlockEnd},
 * {@link MarkdomHandler#onHeadingBlockEnd(MarkdomHeadingLevel) onHeadingBlockEnd},
 * {@link MarkdomHandler#onOrderedListBlockEnd(int) onOrderedListBlockEnd},
 * {@link MarkdomHandler#onEmphasisContentEnd(MarkdomEmphasisLevel) onEmphasisContentEnd},
 * {@link MarkdomHandler#onLinkContentEnd(String) onLinkContentEnd}).</li>
 * </ul>
 */
public final class SimpleMarkdomHandler<Result> {

	private final Stack<Boolean> firsts = new Stack<>();

	private final Stack<Object> parameters = new Stack<Object>();

	private final MarkdomHandler<Result> handler;

	public SimpleMarkdomHandler(MarkdomHandler<Result> handler) {
		if (null == handler) {
			throw new IllegalArgumentException("The given Markdom handler is null");
		}
		this.handler = handler;
	}

	public void onDocumentBegin() {
		handler.onDocumentBegin();
		handler.onBlocksBegin();
		firsts.push(true);
	}

	public void onDocumentEnd() {
		firsts.pop();
		handler.onBlocksEnd();
		handler.onDocumentEnd();
	}

	public void onCodeBlock(String code, Optional<String> hint) {
		nextBlock();
		handler.onBlockBegin(MarkdomBlockType.CODE);
		handler.onCodeBlock(code, hint);
		handler.onBlockEnd(MarkdomBlockType.CODE);
	}

	public void onCommentBlock(String comment) {
		nextBlock();
		handler.onBlockBegin(MarkdomBlockType.COMMENT);
		handler.onCommentBlock(comment);
		handler.onBlockEnd(MarkdomBlockType.COMMENT);
	}

	public void onDivisionBlock() {
		nextBlock();
		handler.onBlockBegin(MarkdomBlockType.DIVISION);
		handler.onDivisionBlock();
		handler.onBlockEnd(MarkdomBlockType.DIVISION);
	}

	public void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		nextBlock();
		parameters.push(level);
		handler.onBlockBegin(MarkdomBlockType.HEADING);
		handler.onHeadingBlockBegin(level);
		handler.onContentsBegin();
		firsts.push(true);
	}

	public void onHeadingBlockEnd() {
		firsts.pop();
		handler.onContentsEnd();
		handler.onHeadingBlockEnd((MarkdomHeadingLevel) parameters.pop());
		handler.onBlockEnd(MarkdomBlockType.HEADING);
	}

	public void onOrderedListBlockBegin(Integer startIndex) {
		nextBlock();
		parameters.push(startIndex);
		handler.onBlockBegin(MarkdomBlockType.ORDERED_LIST);
		handler.onOrderedListBlockBegin(startIndex);
		handler.onListItemsBegin();
		firsts.push(true);
	}

	public void onOrderedListBlockEnd() {
		firsts.pop();
		handler.onListItemsEnd();
		handler.onOrderedListBlockEnd((Integer) parameters.pop());
		handler.onBlockEnd(MarkdomBlockType.ORDERED_LIST);
	}

	public void onParagraphBlockBegin() {
		nextBlock();
		handler.onBlockBegin(MarkdomBlockType.PARAGRAPH);
		handler.onParagraphBlockBegin();
		handler.onContentsBegin();
		firsts.push(true);
	}

	public void onParagraphBlockEnd() {
		firsts.pop();
		handler.onContentsEnd();
		handler.onParagraphBlockEnd();
		handler.onBlockEnd(MarkdomBlockType.PARAGRAPH);
	}

	public void onQuoteBlockBegin() {
		nextBlock();
		handler.onBlockBegin(MarkdomBlockType.QUOTE);
		handler.onQuoteBlockBegin();
		handler.onBlocksBegin();
		firsts.push(true);
	}

	public void onQuoteBlockEnd() {
		firsts.pop();
		handler.onBlocksEnd();
		handler.onQuoteBlockEnd();
		handler.onBlockEnd(MarkdomBlockType.QUOTE);
	}

	public void onUnorderedListBlockBegin() {
		nextBlock();
		handler.onBlockBegin(MarkdomBlockType.UNORDERED_LIST);
		handler.onUnorderedListBlockBegin();
		handler.onListItemsBegin();
		firsts.push(true);
	}

	public void onUnorderedListBlockEnd() {
		firsts.pop();
		handler.onListItemsEnd();
		handler.onUnorderedListBlockEnd();
		handler.onBlockEnd(MarkdomBlockType.UNORDERED_LIST);
	}

	public void onListItemBegin() {
		nextListItem();
		handler.onListItemBegin();
		handler.onBlocksBegin();
		firsts.push(true);
	}

	public void onListItemEnd() {
		firsts.pop();
		handler.onBlocksEnd();
		handler.onListItemEnd();
	}

	public void onCodeContent(String code) {
		nextContent();
		handler.onContentBegin(MarkdomContentType.CODE);
		handler.onCodeContent(code);
		handler.onContentEnd(MarkdomContentType.CODE);
	}

	public void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		nextContent();
		parameters.push(level);
		handler.onContentBegin(MarkdomContentType.EMPHASIS);
		handler.onEmphasisContentBegin(level);
		handler.onContentsBegin();
		firsts.push(true);
	}

	public void onEmphasisContentEnd() {
		firsts.pop();
		handler.onContentsEnd();
		handler.onEmphasisContentEnd((MarkdomEmphasisLevel) parameters.pop());
		handler.onContentEnd(MarkdomContentType.EMPHASIS);
	}

	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		nextContent();
		handler.onContentBegin(MarkdomContentType.IMAGE);
		handler.onImageContent(uri, title, alternative);
		handler.onContentEnd(MarkdomContentType.IMAGE);
	}

	public void onLineBreakContent(Boolean hard) {
		nextContent();
		handler.onContentBegin(MarkdomContentType.LINE_BREAK);
		handler.onLineBreakContent(hard);
		handler.onContentEnd(MarkdomContentType.LINE_BREAK);
	}

	public void onLinkContentBegin(String uri, Optional<String> title) {
		nextContent();
		parameters.push(uri);
		parameters.push(title);
		handler.onContentBegin(MarkdomContentType.LINK);
		handler.onLinkContentBegin(uri, title);
		handler.onContentsBegin();
		firsts.push(true);
	}

	@SuppressWarnings("unchecked")
	public void onLinkContentEnd() {
		firsts.pop();
		handler.onContentsEnd();
		handler.onLinkContentEnd((String) parameters.pop(), (Optional<String>) parameters.pop());
		handler.onContentEnd(MarkdomContentType.LINK);
	}

	public void onTextContent(String text) {
		nextContent();
		handler.onContentBegin(MarkdomContentType.TEXT);
		handler.onTextContent(text);
		handler.onContentEnd(MarkdomContentType.TEXT);
	}

	public Result getResult() {
		return handler.getResult();
	}

	private void nextBlock() {
		if (firsts.firstElement()) {
			firsts.push(false);
		} else {
			handler.onNextBlock();
		}
	}

	private void nextListItem() {
		if (firsts.firstElement()) {
			firsts.push(false);
		} else {
			handler.onNextListItem();
		}
	}

	private void nextContent() {
		if (firsts.firstElement()) {
			firsts.push(false);
		} else {
			handler.onNextContent();
		}
	}

}
