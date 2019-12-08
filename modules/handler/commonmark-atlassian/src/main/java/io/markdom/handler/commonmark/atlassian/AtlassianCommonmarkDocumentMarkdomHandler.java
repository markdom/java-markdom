package io.markdom.handler.commonmark.atlassian;

import java.util.Optional;

import org.commonmark.node.BlockQuote;
import org.commonmark.node.BulletList;
import org.commonmark.node.Code;
import org.commonmark.node.Document;
import org.commonmark.node.Emphasis;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.HardLineBreak;
import org.commonmark.node.Heading;
import org.commonmark.node.HtmlBlock;
import org.commonmark.node.Image;
import org.commonmark.node.Link;
import org.commonmark.node.ListItem;
import org.commonmark.node.Node;
import org.commonmark.node.OrderedList;
import org.commonmark.node.Paragraph;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.StrongEmphasis;
import org.commonmark.node.Text;
import org.commonmark.node.ThematicBreak;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.handler.MarkdomHandler;

public final class AtlassianCommonmarkDocumentMarkdomHandler implements MarkdomHandler<Document> {

	private final Document document = new Document();

	private Node parent = document;

	@Override
	public final void onDocumentBegin() {
	}

	@Override
	public final void onBlocksBegin() {
	}

	@Override
	public final void onBlockBegin(MarkdomBlockType type) {
	}

	@Override
	public final void onCodeBlock(String code, Optional<String> hint) {
		FencedCodeBlock codeBlockNode = new FencedCodeBlock();
		codeBlockNode.setLiteral(code);
		codeBlockNode.setInfo(hint.orElse(null));
		parent.appendChild(codeBlockNode);
	}

	@Override
	public final void onCommentBlock(String comment) {
		HtmlBlock htmlBlockNode = new HtmlBlock();
		htmlBlockNode.setLiteral("<!-- " + comment + " -->");
		parent.appendChild(htmlBlockNode);
	}

	@Override
	public final void onDivisionBlock() {
		parent.appendChild(new ThematicBreak());
	}

	@Override
	public final void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		Heading headingNode = new Heading();
		headingNode.setLevel(level.toOrdinal() + 1);
		pushParent(headingNode);
	}

	@Override
	public final void onHeadingBlockEnd(MarkdomHeadingLevel level) {
		popParent();
	}

	@Override
	public final void onOrderedListBlockBegin(Integer startIndex) {
		OrderedList orderedListNode = new OrderedList();
		orderedListNode.setStartNumber(startIndex);
		pushParent(orderedListNode);
	}

	@Override
	public final void onOrderedListBlockEnd(Integer startIndex) {
		popParent();
	}

	@Override
	public final void onParagraphBlockBegin() {
		pushParent(new Paragraph());
	}

	@Override
	public final void onParagraphBlockEnd() {
		popParent();
	}

	@Override
	public final void onQuoteBlockBegin() {
		pushParent(new BlockQuote());
	}

	@Override
	public final void onQuoteBlockEnd() {
		popParent();
	}

	@Override
	public final void onUnorderedListBlockBegin() {
		pushParent(new BulletList());
	}

	@Override
	public final void onUnorderedListBlockEnd() {
		popParent();
	}

	@Override
	public final void onBlockEnd(MarkdomBlockType type) {
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
		pushParent(new ListItem());
	}

	@Override
	public final void onListItemEnd() {
		popParent();
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
		parent.appendChild(new Code(code));
	}

	@Override
	public final void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		switch (level) {
			case LEVEL_1:
				pushParent(new Emphasis());
				break;
			case LEVEL_2:
				pushParent(new StrongEmphasis());
				break;
		}
	}

	@Override
	public final void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
		popParent();
	}

	@Override
	public final void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		Image imageNode = new Image();
		imageNode.setDestination(uri);
		imageNode.setTitle(title.orElse(null));
		if (alternative.isPresent()) {
			imageNode.appendChild(new Text(alternative.get()));
		}
		parent.appendChild(imageNode);
	}

	@Override
	public final void onLineBreakContent(Boolean hard) {
		if (hard) {
			parent.appendChild(new HardLineBreak());
		} else {
			parent.appendChild(new SoftLineBreak());
		}
	}

	@Override
	public final void onLinkContentBegin(String uri, Optional<String> title) {
		Link linkNode = new Link();
		linkNode.setDestination(uri);
		linkNode.setTitle(title.orElse(null));
		pushParent(linkNode);
	}

	@Override
	public final void onLinkContentEnd(String uri, Optional<String> title) {
		popParent();
	}

	@Override
	public final void onTextContent(String text) {
		parent.appendChild(new Text(text));
	}

	@Override
	public final void onContentEnd(MarkdomContentType type) {
	}

	@Override
	public final void onNextContent() {
	}

	@Override
	public final void onContentsEnd() {
	}

	@Override
	public final void onDocumentEnd() {
	}

	@Override
	public Document getResult() {
		return document;
	}

	private void pushParent(Node node) {
		parent.appendChild(node);
		parent = node;
	}

	private void popParent() {
		parent = parent.getParent();
	}

}
