package io.markdom.handler.commonmark;

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
import org.commonmark.node.ListItem;
import org.commonmark.node.OrderedList;
import org.commonmark.node.Paragraph;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.StrongEmphasis;
import org.commonmark.node.Text;
import org.commonmark.node.ThematicBreak;

class CommonmarkTextVisitor extends AbstractVisitor {

	private final StringBuilder builder;

	public CommonmarkTextVisitor(StringBuilder builder) {
		this.builder = builder;
	}

	@Override
	public void visit(BlockQuote blockQuote) {
	}

	@Override
	public void visit(BulletList bulletList) {
	}

	@Override
	public void visit(Code code) {
		builder.append(code.getLiteral());
	}

	@Override
	public void visit(Document document) {
	}

	@Override
	public void visit(Emphasis emphasis) {
		visitChildren(emphasis);
	}

	@Override
	public void visit(FencedCodeBlock fencedCodeBlock) {
	}

	@Override
	public void visit(HardLineBreak hardLineBreak) {
		if (0 == builder.length() || builder.charAt(builder.length() - 1) != ' ') {
			builder.append(' ');
		}
	}

	@Override
	public void visit(Heading heading) {
	}

	@Override
	public void visit(ThematicBreak thematicBreak) {
	}

	@Override
	public void visit(HtmlInline htmlInline) {
	}

	@Override
	public void visit(HtmlBlock htmlBlock) {
	}

	@Override
	public void visit(Image image) {
		visitChildren(image);
	}

	@Override
	public void visit(IndentedCodeBlock indentedCodeBlock) {
	}

	@Override
	public void visit(Link link) {
		visitChildren(link);
	}

	@Override
	public void visit(ListItem listItem) {
	}

	@Override
	public void visit(OrderedList orderedList) {
	}

	@Override
	public void visit(Paragraph paragraph) {
	}

	@Override
	public void visit(SoftLineBreak softLineBreak) {
	}

	@Override
	public void visit(StrongEmphasis strongEmphasis) {
		visitChildren(strongEmphasis);
	}

	@Override
	public void visit(Text text) {
		builder.append(text.getLiteral());
	}

	@Override
	public void visit(CustomBlock customBlock) {
	}

	@Override
	public void visit(CustomNode customNode) {
	}

}
