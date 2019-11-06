package io.markdom.handler.xml;

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

public abstract class AbstractXmlDocumentMarkdomDispatcher<XmlElement> implements MarkdomDispatcher {

	private SimpleMarkdomHandler<?> handler;

	@Override
	public final <Result> Result handle(MarkdomHandler<Result> handler) {
		if (null == handler) {
			throw new IllegalArgumentException("The given Markdom handler is null");
		}
		this.handler = new SimpleMarkdomHandler<Result>(handler);
		try {
			XmlElement documentElement = getRootElement();
			checkVersion(reqString(documentElement, MarkdomKeys.VERSION));
			document(documentElement);
			return handler.getResult();
		} catch (Exception e) {
			throw new MarkdomException(e);
		}
	}

	private void checkVersion(String version) {
		if (!"1.0".equals(version)) {
			throw new MarkdomException("Invalid version string: " + version);
		}
		int separatorIndex = version.indexOf('.');
		int major = Integer.parseInt(version.substring(0, separatorIndex));
		int minor = Integer.parseInt(version.substring(separatorIndex + 1));
		if (major != 1) {
			throw new MarkdomException("Unsupported major version: " + major);
		} else if (minor < 0) {
			throw new MarkdomException("Unsupported minor version: " + minor);
		}
	}

	private void document(XmlElement documentElement) {
		handler.onDocumentBegin();
		blocks(documentElement);
		handler.onDocumentEnd();
	}

	private void blocks(XmlElement blockParentElement) {
		getElements(blockParentElement).forEachRemaining(this::block);
	}

	private void block(XmlElement blockElement) {
		MarkdomBlockType type = MarkdomBlockType.fromName(getName(blockElement));
		switch (type) {
		case CODE:
			codeBlock(blockElement);
			return;
		case COMMENT:
			commentBlock(blockElement);
			return;
		case DIVISION:
			divisionBlock(blockElement);
			return;
		case HEADING:
			headingBlock(blockElement);
			return;
		case ORDERED_LIST:
			orderedListBlock(blockElement);
			return;
		case PARAGRAPH:
			paragraphBlock(blockElement);
			return;
		case QUOTE:
			quoteBlock(blockElement);
			return;
		case UNORDERED_LIST:
			unorderedListBlock(blockElement);
			return;
		}
		throw new InternalError("Unexpected block type: " + type);
	}

	private void codeBlock(XmlElement blockElement) {
		String code = getText(blockElement);
		Optional<String> hint = optString(blockElement, MarkdomKeys.HINT);
		handler.onCodeBlock(code, hint);
	}

	private void commentBlock(XmlElement blockElement) {
		String comment = getText(blockElement);
		handler.onCommentBlock(comment);
	}

	private void divisionBlock(XmlElement blockElement) {
		handler.onDivisionBlock();
	}

	private void headingBlock(XmlElement blockElement) {
		MarkdomHeadingLevel level = MarkdomHeadingLevel.values()[reqInteger(blockElement, MarkdomKeys.LEVEL) - 1];
		handler.onHeadingBlockBegin(level);
		contents(blockElement);
		handler.onHeadingBlockEnd();
	}

	private void orderedListBlock(XmlElement blockElement) {
		Integer startIndex = reqInteger(blockElement, MarkdomKeys.START_INDEX);
		handler.onOrderedListBlockBegin(startIndex);
		items(blockElement);
		handler.onOrderedListBlockEnd();
	}

	private void paragraphBlock(XmlElement blockElement) {
		handler.onParagraphBlockBegin();
		contents(blockElement);
		handler.onParagraphBlockEnd();
	}

	private void quoteBlock(XmlElement blockElement) {
		handler.onQuoteBlockBegin();
		blocks(blockElement);
		handler.onQuoteBlockEnd();
	}

	private void unorderedListBlock(XmlElement blockElement) {
		handler.onUnorderedListBlockBegin();
		items(blockElement);
		handler.onUnorderedListBlockEnd();
	}

	private void items(XmlElement listBlockElement) {
		getElements(listBlockElement).forEachRemaining(this::item);
	}

	private void item(XmlElement itemElement) {
		handler.onListItemBegin();
		blocks(itemElement);
		handler.onListItemEnd();
	}

	private void contents(XmlElement contentParentElement) {
		getElements(contentParentElement).forEachRemaining(this::content);
	}

	private void content(XmlElement contentElement) {
		MarkdomContentType type = MarkdomContentType.fromName(getName(contentElement));
		switch (type) {
		case CODE:
			codeContent(contentElement);
			return;
		case EMPHASIS:
			emphasisContent(contentElement);
			return;
		case IMAGE:
			imageContent(contentElement);
			return;
		case LINE_BREAK:
			lineBreakContent(contentElement);
			return;
		case LINK:
			linkContent(contentElement);
			return;
		case TEXT:
			textContent(contentElement);
			return;
		}
		throw new InternalError("Unexpected content type: " + type);
	}

	private void codeContent(XmlElement contentElement) {
		String code = getText(contentElement);
		handler.onCodeContent(code);
	}

	private void emphasisContent(XmlElement contentElement) {
		MarkdomEmphasisLevel level = MarkdomEmphasisLevel.values()[reqInteger(contentElement, MarkdomKeys.LEVEL) - 1];
		handler.onEmphasisContentBegin(level);
		contents(contentElement);
		handler.onEmphasisContentEnd();
	}

	private void imageContent(XmlElement contentElement) {
		String uri = reqString(contentElement, MarkdomKeys.URI);
		Optional<String> title = optString(contentElement, MarkdomKeys.TITLE);
		Optional<String> alternative = optString(contentElement, MarkdomKeys.ALTERNATIVE);
		handler.onImageContent(uri, title, alternative);
	}

	private void lineBreakContent(XmlElement contentElement) {
		boolean hard = reqBoolean(contentElement, MarkdomKeys.HARD);
		handler.onLineBreakContent(hard);
	}

	private void linkContent(XmlElement contentElement) {
		String uri = reqString(contentElement, MarkdomKeys.URI);
		Optional<String> title = optString(contentElement, MarkdomKeys.TITLE);
		handler.onLinkContentBegin(uri, title);
		contents(contentElement);
		handler.onLinkContentEnd();
	}

	private void textContent(XmlElement contentElement) {
		String text = getText(contentElement);
		handler.onTextContent(text);
	}

	protected abstract XmlElement getRootElement();

	protected abstract String getName(XmlElement xmlElement);

	protected abstract Iterator<XmlElement> getElements(XmlElement xmlElement);

	protected abstract String getText(XmlElement xmlElement);

	protected abstract Optional<String> optString(XmlElement xmlElement, String key);

	protected abstract String reqString(XmlElement xmlElement, String key);

	protected abstract Boolean reqBoolean(XmlElement xmlElement, String key);

	protected abstract Integer reqInteger(XmlElement xmlElement, String key);

}
