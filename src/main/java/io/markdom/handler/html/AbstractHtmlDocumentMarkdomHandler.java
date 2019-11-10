package io.markdom.handler.html;

import java.util.Optional;
import java.util.Stack;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.handler.MarkdomHandler;
import io.markdom.util.Attribute;
import io.markdom.util.Element;
import io.markdom.util.ObjectHelper;

public abstract class AbstractHtmlDocumentMarkdomHandler<Result> implements MarkdomHandler<Result> {

	private final Stack<Integer> depths = new Stack<Integer>();

	private final HtmlDelegate delegate;

	private final String title;

	public AbstractHtmlDocumentMarkdomHandler(HtmlDelegate delegate, String title) {
		this.delegate = ObjectHelper.notNull("delegate", delegate);
		this.title = ObjectHelper.notNull("title", title);
	}

	@Override
	public final void onDocumentBegin() {
		beginDocument("html", "html");
		pushElement("head");
		pushElement("title");
		setText(title);
		popElement();
		popElement();
		pushElement("body");
	}

	@Override
	public final void onDocumentEnd() {
		popElement();
		endDocument();
	}

	@Override
	public final void onBlocksBegin() {
	}

	@Override
	public final void onBlockBegin(MarkdomBlockType type) {
	}

	@Override
	public final void onCodeBlock(String code, Optional<String> hint) {
		pushElements(delegate.onCodeBlock(code, hint));
		setCharacterData(code);
	}

	@Override
	public final void onCommentBlock(String comment) {
	}

	@Override
	public final void onDivisionBlock() {
		pushElements(delegate.onDivisionBlock());
	}

	@Override
	public final void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		pushElements(delegate.onHeadingBlock(level));
	}

	@Override
	public final void onHeadingBlockEnd(MarkdomHeadingLevel level) {
	}

	@Override
	public final void onOrderedListBlockBegin(Integer startIndex) {
		pushElements(delegate.onOrderdListBlock(startIndex));
	}

	@Override
	public final void onOrderedListBlockEnd(Integer startIndex) {
	}

	@Override
	public final void onParagraphBlockBegin() {
		pushElements(delegate.onParagraphBlock());
	}

	@Override
	public final void onParagraphBlockEnd() {
	}

	@Override
	public final void onQuoteBlockBegin() {
		pushElements(delegate.onQuoteBlock());
	}

	@Override
	public final void onQuoteBlockEnd() {
	}

	@Override
	public final void onUnorderedListBlockBegin() {
		pushElements(delegate.onUnorderedListBlock());
	}

	@Override
	public final void onUnorderedListBlockEnd() {
	}

	@Override
	public final void onBlockEnd(MarkdomBlockType type) {
		popElements();
	}

	@Override
	public final void onNextBlock() {
	}

	@Override
	public final void onBlocksEnd() {
	}

	@Override
	public final void onListItemsBegin() {
	}

	@Override
	public final void onListItemBegin() {
		pushElements(delegate.onListItem());
	}

	@Override
	public final void onListItemEnd() {
		popElements();
	}

	@Override
	public final void onNextListItem() {
	}

	@Override
	public final void onListItemsEnd() {
	}

	@Override
	public final void onContentsBegin() {
	}

	@Override
	public final void onContentBegin(MarkdomContentType type) {
	}

	@Override
	public final void onCodeContent(String code) {
		pushElements(delegate.onCodeContent(code));
		setText(code);
	}

	@Override
	public final void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		pushElements(delegate.onEmphasisContent(level));
	}

	@Override
	public final void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
	}

	@Override
	public final void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		pushElements(delegate.onImageContent(uri, title, alternative));
	}

	@Override
	public final void onLineBreakContent(Boolean hard) {
		if (hard) {
			pushElements(delegate.onLineBreakContent());
		}
	}

	@Override
	public final void onLinkContentBegin(String uri, Optional<String> title) {
		pushElements(delegate.onLinkContent(uri, title));
	}

	@Override
	public final void onLinkContentEnd(String uri, Optional<String> title) {
	}

	@Override
	public final void onTextContent(String text) {
		pushElements(delegate.onTextContent(text));
		if (0 != text.length() && hasWhitespaceBorder(text)) {
			setCharacterData(text);
		} else {
			setText(text);
		}
	}

	private boolean hasWhitespaceBorder(String text) {
		return Character.isWhitespace(text.charAt(0)) || Character.isWhitespace(text.charAt(text.length() - 1));
	}

	@Override
	public final void onContentEnd(MarkdomContentType type) {
		popElements();
	}

	@Override
	public final void onNextContent() {
	}

	@Override
	public final void onContentsEnd() {
	}

	protected abstract void beginDocument(String dtdQualifiedName, String rootTagName);

	private void pushElements(Iterable<Element> elements) {
		int depth = 0;
		for (Element element : elements) {
			pushElement(element.getTagName());
			setAttributes(element.getAttributes());
			depth++;
		}
		depths.push(depth);
	}

	protected abstract void pushElement(String tagName);

	protected abstract void setAttributes(Iterable<Attribute> attributes);

	protected abstract void setCharacterData(String text);

	protected abstract void setText(String text);

	private void popElements() {
		int depth = depths.pop();
		for (int i = 0; i < depth; i++) {
			popElement();
		}
	}

	protected abstract void popElement();

	protected abstract void endDocument();

}
