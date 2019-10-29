package io.markdom.handler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomException;
import io.markdom.common.MarkdomHeadingLevel;

public final class ValidatingMarkdomHandler<Result> implements MarkdomHandler<Result> {

	private final MarkdomHandler<Result> handler;

	private String currentLinkUri;

	private boolean insideHeading;

	public ValidatingMarkdomHandler(MarkdomHandler<Result> handler) {
		if (null == handler) {
			throw new IllegalArgumentException("The given Markdom handler is null");
		}
		this.handler = handler;
	}

	@Override
	public void onDocumentBegin() {
		handler.onDocumentBegin();
	}

	@Override
	public void onDocumentEnd() {
		handler.onDocumentEnd();
	}

	@Override
	public void onBlocksBegin() {
		handler.onBlocksBegin();
	}

	@Override
	public void onBlockBegin(MarkdomBlockType type) {
		handler.onBlockBegin(type);
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
		validateString(hint, "hint");
		handler.onCodeBlock(code, hint);
	}

	@Override
	public void onCommentBlock(String comment) {
		handler.onCommentBlock(comment);

	}

	@Override
	public void onDivisionBlock() {
		handler.onDivisionBlock();
	}

	@Override
	public void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		insideHeading = true;
		handler.onHeadingBlockBegin(level);
	}

	@Override
	public void onHeadingBlockEnd(MarkdomHeadingLevel level) {
		insideHeading = false;
		handler.onHeadingBlockEnd(level);
	}

	@Override
	public void onUnorderedListBlockBegin() {
		handler.onUnorderedListBlockBegin();
	}

	@Override
	public void onOrderedListBlockBegin(Integer startIndex) {
		if (startIndex < 0) {
			throw new MarkdomException("The given start index is negative: " + startIndex);
		}
		handler.onOrderedListBlockBegin(startIndex);
	}

	@Override
	public void onListItemsBegin() {
		handler.onListItemsBegin();
	}

	@Override
	public void onListItemBegin() {
		handler.onListItemBegin();
	}

	@Override
	public void onListItemEnd() {
		handler.onListItemEnd();
	}

	@Override
	public void onNextListItem() {
		handler.onNextListItem();
	}

	@Override
	public void onListItemsEnd() {
		handler.onListItemsEnd();
	}

	@Override
	public void onUnorderedListBlockEnd() {
		handler.onUnorderedListBlockEnd();
	}

	@Override
	public void onOrderedListBlockEnd(Integer startIndex) {
		handler.onOrderedListBlockEnd(startIndex);
	}

	@Override
	public void onParagraphBlockBegin() {
		handler.onParagraphBlockBegin();
	}

	@Override
	public void onParagraphBlockEnd() {
		handler.onParagraphBlockEnd();
	}

	@Override
	public void onQuoteBlockBegin() {
		handler.onQuoteBlockBegin();
	}

	@Override
	public void onQuoteBlockEnd() {
		handler.onQuoteBlockEnd();
	}

	@Override
	public void onBlockEnd(MarkdomBlockType type) {
		handler.onBlockEnd(type);
	}

	@Override
	public void onNextBlock() {
		handler.onNextBlock();
	}

	@Override
	public void onBlocksEnd() {
		handler.onBlocksEnd();
	}

	@Override
	public void onContentsBegin() {
		handler.onContentsBegin();
	}

	@Override
	public void onContentBegin(MarkdomContentType type) {
		handler.onContentBegin(type);
	}

	@Override
	public void onCodeContent(String code) {
		validateString(code, "code");
		handler.onCodeContent(code);
	}

	@Override
	public void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		handler.onEmphasisContentBegin(level);
	}

	@Override
	public void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
		handler.onEmphasisContentEnd(level);
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		validateUri(uri);
		validateString(title, "title");
		validateString(alternative, "alternative");
		handler.onImageContent(uri, title, alternative);
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		if (insideHeading) {
			throw new MarkdomException("Markdom document contains a line break inside a heading");
		}
		handler.onLineBreakContent(hard);
	}

	@Override
	public void onLinkContentBegin(String uri, Optional<String> title) {
		if (null != currentLinkUri) {
			throw new MarkdomException("Markdom document contains a nested link to '" + uri + "' inside link to '" + currentLinkUri + "'");
		}
		validateUri(uri);
		validateString(title, "title");
		currentLinkUri = uri;
		handler.onLinkContentBegin(uri, title);
	}

	@Override
	public void onLinkContentEnd(String uri, Optional<String> title) {
		currentLinkUri = null;
		handler.onLinkContentEnd(uri, title);
	}

	@Override
	public void onTextContent(String text) {
		validateString(text, "text");
		handler.onTextContent(text);
	}

	@Override
	public void onContentEnd(MarkdomContentType type) {
		handler.onContentEnd(type);
	}

	@Override
	public void onNextContent() {
		handler.onNextContent();
	}

	@Override
	public void onContentsEnd() {
		handler.onContentsEnd();
	}

	@Override
	public Result getResult() {
		return handler.getResult();
	}

	private void validateString(Optional<String> string, String name) {
		if (string.isPresent()) {
			validateString(string.get(), name);
		}
	}

	private void validateString(String string, String name) {
		for (int i = 0, n = string.length(); i < n; i++) {
			if ('\n' == string.charAt(i)) {
				throw new MarkdomException("The given " + name + " contains a line break at index " + i + ": " + string);
			}
		}
	}

	private void validateUri(String uri) {
		try {
			new URI(uri);
		} catch (URISyntaxException e) {
			throw new MarkdomException("The given URI is invalid: " + uri, e);
		}
	}

}
