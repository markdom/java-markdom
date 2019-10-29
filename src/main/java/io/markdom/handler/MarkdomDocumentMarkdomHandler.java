package io.markdom.handler;

import java.util.Optional;
import java.util.Stack;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.model.MarkdomBlockParent;
import io.markdom.model.MarkdomCodeBlock;
import io.markdom.model.MarkdomCodeContent;
import io.markdom.model.MarkdomCommentBlock;
import io.markdom.model.MarkdomContentParent;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomEmphasisContent;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomHeadingBlock;
import io.markdom.model.MarkdomImageContent;
import io.markdom.model.MarkdomLinkContent;
import io.markdom.model.MarkdomListBlock;
import io.markdom.model.MarkdomListItem;
import io.markdom.model.MarkdomOrderedListBlock;
import io.markdom.model.MarkdomParagraphBlock;
import io.markdom.model.MarkdomQuoteBlock;
import io.markdom.model.MarkdomTextContent;
import io.markdom.model.MarkdomUnorderedListBlock;

public final class MarkdomDocumentMarkdomHandler extends AbstractMarkdomHandler<MarkdomDocument> {

	private final MarkdomFactory factory;

	private final MarkdomDocument document;

	private final Stack<MarkdomListBlock> listBlocks = new Stack<MarkdomListBlock>();

	private final Stack<MarkdomBlockParent> blockParents = new Stack<MarkdomBlockParent>();

	private final Stack<MarkdomContentParent> contentParents = new Stack<MarkdomContentParent>();

	public MarkdomDocumentMarkdomHandler(MarkdomFactory factory) {
		if (null == factory) {
			throw new IllegalArgumentException("The given Markdom factory is null");
		}
		this.factory = factory;
		this.document = factory.document();
	}

	@Override
	public void onDocumentBegin() {
		blockParents.push(document);
	}

	@Override
	public void onDocumentEnd() {
		blockParents.pop();
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
		MarkdomCodeBlock block = factory.codeBlock(code, hint);
		blockParents.firstElement().addBlock(block);
	}

	@Override
	public void onCommentBlock(String comment) {
		MarkdomCommentBlock block = factory.commentBlock(comment);
		blockParents.firstElement().addBlock(block);
	}

	@Override
	public void onDivisionBlock() {
		blockParents.firstElement().addBlock(factory.divisionBlock());
	}

	@Override
	public void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		MarkdomHeadingBlock block = factory.headingBlock(level);
		blockParents.firstElement().addBlock(block);
		contentParents.push(block);
	}

	@Override
	public void onHeadingBlockEnd(MarkdomHeadingLevel level) {
		contentParents.pop();
	}

	@Override
	public void onUnorderedListBlockBegin() {
		MarkdomUnorderedListBlock block = factory.unorderedListBlock();
		blockParents.firstElement().addBlock(block);
		listBlocks.push(block);
	}

	@Override
	public void onOrderedListBlockBegin(Integer startIndex) {
		MarkdomOrderedListBlock block = factory.orderedListBlock(startIndex);
		blockParents.firstElement().addBlock(block);
		listBlocks.push(block);
	}

	@Override
	public void onListItemBegin() {
		MarkdomListItem item = factory.listItem();
		listBlocks.firstElement().addListItem(item);
		blockParents.push(item);
	}

	@Override
	public void onListItemEnd() {
		blockParents.pop();
	}

	@Override
	public void onUnorderedListBlockEnd() {
		listBlocks.pop();
	}

	@Override
	public void onOrderedListBlockEnd(Integer startIndex) {
		listBlocks.pop();
	}

	@Override
	public void onParagraphBlockBegin() {
		MarkdomParagraphBlock block = factory.paragraphBlock();
		blockParents.firstElement().addBlock(block);
		contentParents.push(block);
	}

	@Override
	public void onParagraphBlockEnd() {
		contentParents.pop();
	}

	@Override
	public void onQuoteBlockBegin() {
		MarkdomQuoteBlock block = factory.quoteBlock();
		blockParents.firstElement().addBlock(block);
		blockParents.push(block);
	}

	@Override
	public void onQuoteBlockEnd() {
		blockParents.pop();
	}

	@Override
	public void onCodeContent(String code) {
		MarkdomCodeContent content = factory.codeContent(code);
		contentParents.firstElement().addContent(content);
	}

	@Override
	public void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		MarkdomEmphasisContent content = factory.emphasisContent(level);
		contentParents.firstElement().addContent(content);
		contentParents.push(content);
	}

	@Override
	public void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
		contentParents.pop();
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		MarkdomImageContent content = factory.imageContent(uri, title, alternative);
		contentParents.firstElement().addContent(content);
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		contentParents.firstElement().addContent(factory.lineBreakContent(hard));
	}

	@Override
	public void onLinkContentBegin(String uri, Optional<String> title) {
		MarkdomLinkContent content = factory.linkContent(uri, title);
		contentParents.firstElement().addContent(content);
		contentParents.push(content);
	}

	@Override
	public void onLinkContentEnd(String uri, Optional<String> title) {
		contentParents.pop();
	}

	@Override
	public void onTextContent(String text) {
		MarkdomTextContent content = factory.textContent(text);
		contentParents.firstElement().addContent(content);
	}

	@Override
	public MarkdomDocument getResult() {
		return document;
	}

}
