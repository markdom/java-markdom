package io.markdom.handler.commonmark.atlassian;

import java.util.Optional;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.BlockQuote;
import org.commonmark.node.BulletList;
import org.commonmark.node.Code;
import org.commonmark.node.CustomBlock;
import org.commonmark.node.CustomNode;
import org.commonmark.node.Document;
import org.commonmark.node.Emphasis;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.HardLineBreak;
import org.commonmark.node.Heading;
import org.commonmark.node.HtmlBlock;
import org.commonmark.node.HtmlInline;
import org.commonmark.node.Image;
import org.commonmark.node.IndentedCodeBlock;
import org.commonmark.node.Link;
import org.commonmark.node.LinkReferenceDefinition;
import org.commonmark.node.ListItem;
import org.commonmark.node.Node;
import org.commonmark.node.OrderedList;
import org.commonmark.node.Paragraph;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.StrongEmphasis;
import org.commonmark.node.Text;
import org.commonmark.node.ThematicBreak;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomException;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.handler.MarkdomHandler;
import io.markdom.handler.SimpleMarkdomHandler;

final class MarkdomHandlerCommonmarkVisitor<Result> extends AbstractVisitor {

	private static final String COMMENT_BEGIN = "<!--";

	private static final String COMMENT_END = "-->";

	private final SimpleMarkdomHandler<Result> handler;

	MarkdomHandlerCommonmarkVisitor(MarkdomHandler<Result> handler) {
		this.handler = new SimpleMarkdomHandler<>(handler);
	}

	@Override
	public void visit(BlockQuote blockQuote) {
		handler.onQuoteBlockBegin();
		visitChildren(blockQuote);
		handler.onQuoteBlockEnd();
	}

	@Override
	public void visit(BulletList bulletList) {
		handler.onUnorderedListBlockBegin();
		visitChildren(bulletList);
		handler.onUnorderedListBlockEnd();
	}

	@Override
	public void visit(Code code) {
		handler.onCodeContent(code.getLiteral());
	}

	@Override
	public void visit(CustomNode customNode) {
		throw new MarkdomException("Encountered custom node: " + customNode);
	}

	@Override
	public void visit(CustomBlock customBlock) {
		throw new MarkdomException("Encountered custom block: " + customBlock);
	}

	@Override
	public void visit(Document document) {
		handler.onDocumentBegin();
		visitChildren(document);
		handler.onDocumentEnd();
	}

	@Override
	public void visit(Emphasis emphasis) {
		handler.onEmphasisContentBegin(MarkdomEmphasisLevel.LEVEL_1);
		visitChildren(emphasis);
		handler.onEmphasisContentEnd();
	}

	@Override
	public void visit(FencedCodeBlock fencedCodeBlock) {
		String code = fencedCodeBlock.getLiteral();
		Optional<String> hint = Optional.ofNullable(fencedCodeBlock.getInfo());
		handler.onCodeBlock(code, hint);
	}

	@Override
	public void visit(HardLineBreak hardLineBreak) {
		handler.onLineBreakContent(true);
	}

	@Override
	public void visit(Heading heading) {
		MarkdomHeadingLevel level = MarkdomHeadingLevel.values()[heading.getLevel() - 1];
		handler.onHeadingBlockBegin(level);
		visitChildren(heading);
		handler.onHeadingBlockEnd();
	}

	@Override
	public void visit(HtmlBlock htmlBlock) {
		String htmlText = htmlBlock.getLiteral();
		if (null != htmlText && htmlText.length() >= COMMENT_BEGIN.length() + COMMENT_END.length()
				&& htmlText.startsWith(COMMENT_BEGIN) && htmlText.endsWith(COMMENT_END)) {
			handler.onCommentBlock(htmlText.substring(COMMENT_BEGIN.length(), htmlText.length() - COMMENT_END.length()));
		} else {
			throw new MarkdomException("Encountered HTML block: " + htmlBlock.getLiteral());
		}
	}

	@Override
	public void visit(HtmlInline htmlInline) {
		throw new MarkdomException("Encountered HTML inline: " + htmlInline.getLiteral());
	}

	@Override
	public void visit(Image image) {
		String uri = image.getDestination();
		Optional<String> title = Optional.ofNullable(image.getTitle());
		Optional<String> alternative = Optional.ofNullable(visitText(image));
		handler.onImageContent(uri, title, alternative);
	}

	private String visitText(Node node) {
		StringBuilder builder = new StringBuilder();
		node.accept(new PlainTextCommonmarkVisitor(builder));
		return 0 == builder.length() ? null : builder.toString();
	}

	@Override
	public void visit(IndentedCodeBlock indentedCodeBlock) {
		handler.onCodeBlock(indentedCodeBlock.getLiteral(), Optional.empty());
	}

	@Override
	public void visit(Link link) {
		String uri = link.getDestination();
		Optional<String> title = Optional.ofNullable(link.getTitle());
		handler.onLinkContentBegin(uri, title);
		visitChildren(link);
		handler.onLinkContentEnd();
	}

	@Override
	public void visit(LinkReferenceDefinition linkReferenceDefinition) {
	}

	@Override
	public void visit(ListItem listItem) {
		handler.onListItemBegin();
		visitChildren(listItem);
		handler.onListItemEnd();
	}

	@Override
	public void visit(OrderedList orderedList) {
		int startIndex = orderedList.getStartNumber();
		handler.onOrderedListBlockBegin(startIndex);
		visitChildren(orderedList);
		handler.onOrderedListBlockEnd();
	}

	@Override
	public void visit(Paragraph paragraph) {
		handler.onParagraphBlockBegin();
		visitChildren(paragraph);
		handler.onParagraphBlockEnd();
	}

	@Override
	public void visit(SoftLineBreak softLineBreak) {
		handler.onLineBreakContent(false);
	}

	@Override
	public void visit(StrongEmphasis strongEmphasis) {
		handler.onEmphasisContentBegin(MarkdomEmphasisLevel.LEVEL_2);
		visitChildren(strongEmphasis);
		handler.onEmphasisContentEnd();
	}

	@Override
	public void visit(Text text) {
		handler.onTextContent(text.getLiteral());
	}

	@Override
	public void visit(ThematicBreak thematicBreak) {
		handler.onDivisionBlock();
	}

}