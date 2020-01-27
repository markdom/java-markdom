package io.markdom.handler.xml;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.common.MarkdomKeys;
import io.markdom.common.MarkdomSchemas;
import io.markdom.handler.MarkdomHandler;
import io.markdom.util.Attribute;
import io.markdom.util.Attributes;

public abstract class AbstractXmlDocumentMarkdomHandler<Result> implements MarkdomHandler<Result> {

	@Override
	public final void onDocumentBegin() {
		beginDocument(null, null, null, "Document", "1.0", MarkdomSchemas.XML);
	}

	@Override
	public final void onBlocksBegin() {
	}

	@Override
	public final void onBlockBegin(MarkdomBlockType type) {
		pushElement(type.toName());
	}

	@Override
	public final void onCodeBlock(String code, Optional<String> hint) {
		setAttributes(new Attributes().add(map(MarkdomKeys.HINT, hint)));
		setCharacterData(code);
	}

	@Override
	public final void onCommentBlock(String comment) {
		setCharacterData(comment);
	}

	@Override
	public final void onDivisionBlock() {
	}

	@Override
	public final void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		setAttributes(new Attributes().add(new Attribute(MarkdomKeys.LEVEL, Integer.toString(level.ordinal() + 1))));
	}

	@Override
	public final void onHeadingBlockEnd(MarkdomHeadingLevel level) {
	}

	@Override
	public final void onOrderedListBlockBegin(Integer startIndex) {
		setAttributes(new Attributes().add(new Attribute(MarkdomKeys.START_INDEX, Integer.toString(startIndex))));
	}

	@Override
	public final void onOrderedListBlockEnd(Integer startIndex) {
	}

	@Override
	public final void onParagraphBlockBegin() {
	}

	@Override
	public final void onParagraphBlockEnd() {
	}

	@Override
	public final void onQuoteBlockBegin() {
	}

	@Override
	public final void onQuoteBlockEnd() {
	}

	@Override
	public final void onUnorderedListBlockBegin() {
	}

	@Override
	public final void onUnorderedListBlockEnd() {
	}

	@Override
	public final void onBlockEnd(MarkdomBlockType type) {
		popElement();
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
		pushElement("ListItem");
	}

	@Override
	public final void onListItemEnd() {
		popElement();
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
		pushElement(type.toName());
	}

	@Override
	public final void onCodeContent(String code) {
		setText(code);
	}

	@Override
	public final void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		setAttributes(new Attributes().add(new Attribute(MarkdomKeys.LEVEL, Integer.toString(level.ordinal() + 1))));
	}

	@Override
	public final void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
	}

	@Override
	public final void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		setAttributes(new Attributes().add(new Attribute(MarkdomKeys.URI, uri)).add(map(MarkdomKeys.TITLE, title))
			.add(map(MarkdomKeys.ALTERNATIVE, alternative)));
	}

	@Override
	public final void onLineBreakContent(Boolean hard) {
		setAttributes(new Attributes().add(new Attribute(MarkdomKeys.HARD, Boolean.toString(hard))));
	}

	@Override
	public final void onLinkContentBegin(String uri, Optional<String> title) {
		setAttributes(new Attributes().add(new Attribute(MarkdomKeys.URI, uri)).add(map(MarkdomKeys.TITLE, title)));
	}

	@Override
	public final void onLinkContentEnd(String uri, Optional<String> title) {
	}

	@Override
	public final void onTextContent(String text) {
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
		popElement();
	}

	@Override
	public final void onNextContent() {
	}

	@Override
	public final void onContentsEnd() {
	}

	@Override
	public final void onDocumentEnd() {
		endDocument();
	}

	private Optional<Attribute> map(String key, Optional<String> value) {
		return value.map(actualValue -> new Attribute(key, actualValue));
	}

	protected abstract void beginDocument(String dtdQualifiedName, String dtdPublicId, String dtdSystemId, String rootTagName,
		String documentVersion, String xmlnsNameSpace);

	protected abstract void pushElement(String tagName);

	protected abstract void setAttributes(Attributes attributes);

	protected abstract void setCharacterData(String text);

	protected abstract void setText(String text);

	protected abstract void popElement();

	protected abstract void endDocument();

}
