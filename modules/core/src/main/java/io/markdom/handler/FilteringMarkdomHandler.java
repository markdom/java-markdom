package io.markdom.handler;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.ObjectHelper;

public final class FilteringMarkdomHandler<Result> implements MarkdomHandler<Result> {

	private final SimpleMarkdomHandler<Result> handler;

	private final MarkdomFilter filter;

	private boolean filtering;

	private int filterDepth;

	public FilteringMarkdomHandler(MarkdomHandler<Result> handler, MarkdomFilter filter) {
		this.handler = new SimpleMarkdomHandler<Result>(ObjectHelper.notNull("handler", handler));
		this.filter = ObjectHelper.notNull("filter", filter);
	}

	@Override
	public void onDocumentBegin() {
		handler.onDocumentBegin();
	}

	@Override
	public void onBlocksBegin() {
	}

	@Override
	public void onBlockBegin(MarkdomBlockType type) {
		if (filtering) {
			filterDepth++;
		}
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
		if (!filtering) {
			if (filter.testCodeBlock(code, hint)) {
				filtering = true;
			} else {
				handler.onCodeBlock(code, hint);
			}
		}
	}

	@Override
	public void onCommentBlock(String comment) {
		if (!filtering) {
			if (filter.testCommentBlock()) {
				filtering = true;
			} else {
				handler.onCommentBlock(comment);
			}
		}
	}

	@Override
	public void onDivisionBlock() {
		if (!filtering) {
			if (filter.testDivisionBlock()) {
				filtering = true;
			} else {
				handler.onDivisionBlock();
			}
		}
	}

	@Override
	public void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		if (!filtering) {
			if (filter.testHeadingBlock(level)) {
				filtering = true;
			} else {
				handler.onHeadingBlockBegin(level);
			}
		}
	}

	@Override
	public void onHeadingBlockEnd(MarkdomHeadingLevel level) {
		if (!filtering) {
			handler.onHeadingBlockEnd();
		}
	}

	@Override
	public void onOrderedListBlockBegin(Integer startIndex) {
		if (!filtering) {
			if (filter.testOrderedListBlock(startIndex)) {
				filtering = true;
			} else {
				handler.onOrderedListBlockBegin(startIndex);
			}
		}
	}

	@Override
	public void onOrderedListBlockEnd(Integer startIndex) {
		if (!filtering) {
			handler.onOrderedListBlockEnd();
		}
	}

	@Override
	public void onParagraphBlockBegin() {
		if (!filtering) {
			if (filter.testParagraphBlock()) {
				filtering = true;
			} else {
				handler.onParagraphBlockBegin();
			}
		}
	}

	@Override
	public void onParagraphBlockEnd() {
		if (!filtering) {
			handler.onParagraphBlockEnd();
		}
	}

	@Override
	public void onQuoteBlockBegin() {
		if (!filtering) {
			if (filter.testQuoteBlock()) {
				filtering = true;
			} else {
				handler.onQuoteBlockBegin();
			}
		}
	}

	@Override
	public void onQuoteBlockEnd() {
		if (!filtering) {
			handler.onQuoteBlockEnd();
		}
	}

	@Override
	public void onUnorderedListBlockBegin() {
		if (!filtering) {
			if (filter.testUnorderedListBlock()) {
				filtering = true;
			} else {
				handler.onUnorderedListBlockBegin();
			}
		}
	}

	@Override
	public void onUnorderedListBlockEnd() {
		if (!filtering) {
			handler.onUnorderedListBlockEnd();
		}
	}

	@Override
	public void onBlockEnd(MarkdomBlockType type) {
		if (filtering) {
			if (0 == filterDepth) {
				filtering = false;
			} else {
				filterDepth--;
			}
		}
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
	public void onListItemBegin() {
		if (!filtering) {
			handler.onListItemBegin();
		}
	}

	@Override
	public void onListItemEnd() {
		if (!filtering) {
			handler.onListItemEnd();
		}
	}

	@Override
	public void onNextListItem() {
	}

	@Override
	public void onListItemsEnd() {
	}

	@Override
	public void onContentsBegin() {
	}

	@Override
	public void onContentBegin(MarkdomContentType type) {
		if (filtering) {
			filterDepth++;
		}
	}

	@Override
	public void onCodeContent(String code) {
		if (!filtering) {
			if (filter.testCodeContent(code)) {
				filtering = true;
			} else {
				handler.onCodeContent(code);
			}
		}
	}

	@Override
	public void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		if (!filtering) {
			if (filter.testEmphasisContent(level)) {
				filtering = true;
			} else {
				handler.onEmphasisContentBegin(level);
			}
		}
	}

	@Override
	public void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
		if (!filtering) {
			handler.onEmphasisContentEnd();
		}
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		if (!filtering) {
			if (filter.testImageContent(uri, title, alternative)) {
				filtering = true;
			} else {
				handler.onImageContent(uri, title, alternative);
			}
		}
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		if (!filtering) {
			if (filter.testLineBreakContent(hard)) {
				filtering = true;
			} else {
				handler.onLineBreakContent(hard);
			}
		}
	}

	@Override
	public void onLinkContentBegin(String uri, Optional<String> title) {
		if (!filtering) {
			if (filter.testLinkContent(uri, title)) {
				filtering = true;
			} else {
				handler.onLinkContentBegin(uri, title);
			}
		}
	}

	@Override
	public void onLinkContentEnd(String uri, Optional<String> title) {
		if (!filtering) {
			handler.onLinkContentEnd();
		}
	}

	@Override
	public void onTextContent(String text) {
		if (!filtering) {
			if (filter.testTextContent(text)) {
				filtering = true;
			} else {
				handler.onTextContent(text);
			}
		}
	}

	@Override
	public void onContentEnd(MarkdomContentType type) {
		if (filtering) {
			if (0 == filterDepth) {
				filtering = false;
			} else {
				filterDepth--;
			}
		}
	}

	@Override
	public void onNextContent() {
	}

	@Override
	public void onContentsEnd() {
	}

	@Override
	public void onDocumentEnd() {
		handler.onDocumentEnd();
	}

	@Override
	public Result getResult() {
		return handler.getResult();
	}

}
