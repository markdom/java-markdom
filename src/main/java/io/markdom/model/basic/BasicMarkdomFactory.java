package io.markdom.model.basic;

import java.util.Optional;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.model.MarkdomBlock;
import io.markdom.model.MarkdomCodeBlock;
import io.markdom.model.MarkdomCodeContent;
import io.markdom.model.MarkdomCommentBlock;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomDivisionBlock;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomEmphasisContent;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomHeadingBlock;
import io.markdom.model.MarkdomImageContent;
import io.markdom.model.MarkdomLineBreakContent;
import io.markdom.model.MarkdomLinkContent;
import io.markdom.model.MarkdomListItem;
import io.markdom.model.MarkdomOrderedListBlock;
import io.markdom.model.MarkdomParagraphBlock;
import io.markdom.model.MarkdomQuoteBlock;
import io.markdom.model.MarkdomTextContent;
import io.markdom.model.MarkdomUnorderedListBlock;

public final class BasicMarkdomFactory implements MarkdomFactory {

	@Override
	public MarkdomCodeBlock codeBlock(String code) {
		return codeBlock(code, Optional.empty());
	}

	@Override
	public MarkdomCodeBlock codeBlock(String code, Optional<String> hint) {
		return new BasicMarkdomCodeBlock(this, code, hint);
	}

	@Override
	public MarkdomCodeContent codeContent(String code) {
		return new BasicMarkdomCodeContent(this, code);
	}

	@Override
	public MarkdomCommentBlock commentBlock(String comment) {
		return new BasicMarkdomCommentBlock(this, comment);
	}

	@Override
	public MarkdomDivisionBlock divisionBlock() {
		return new BasicMarkdomDivisionBlock(this);
	}

	@Override
	public MarkdomDocument document() {
		return new BasicMarkdomDocument(this);
	}

	@Override
	public MarkdomDocument document(MarkdomBlock... blocks) {
		return new BasicMarkdomDocument(this).addBlocks(blocks);
	}

	@Override
	public MarkdomDocument document(Iterable<MarkdomBlock> blocks) {
		return new BasicMarkdomDocument(this).addBlocks(blocks);
	}

	@Override
	public MarkdomEmphasisContent emphasisContent(MarkdomEmphasisLevel level) {
		return new BasicMarkdomEmphasisContent(this, level);
	}

	@Override
	public MarkdomEmphasisContent emphasisContent(MarkdomEmphasisLevel level, MarkdomContent... contents) {
		return emphasisContent(level).addContents(contents);
	}

	@Override
	public MarkdomEmphasisContent emphasisContent(MarkdomEmphasisLevel level, Iterable<MarkdomContent> contents) {
		return emphasisContent(level).addContents(contents);
	}

	@Override
	public MarkdomHeadingBlock headingBlock(MarkdomHeadingLevel level) {
		return new BasicMarkdomHeadingBlock(this, level);
	}

	@Override
	public MarkdomHeadingBlock headingBlock(MarkdomHeadingLevel level, MarkdomContent... contents) {
		return headingBlock(level).addContents(contents);
	}

	@Override
	public MarkdomHeadingBlock headingBlock(MarkdomHeadingLevel level, Iterable<MarkdomContent> contents) {
		return headingBlock(level).addContents(contents);
	}

	@Override
	public MarkdomImageContent imageContent(String uri) {
		return imageContent(uri, Optional.empty(), Optional.empty());
	}

	@Override
	public MarkdomImageContent imageContent(String uri, Optional<String> title, Optional<String> alternative) {
		return new BasicMarkdomImageContent(this, uri, title, alternative);
	}

	@Override
	public MarkdomLineBreakContent lineBreakContent(Boolean hard) {
		return new BasicMarkdomLineBreakContent(this, hard);
	}

	@Override
	public MarkdomLinkContent linkContent(String uri) {
		return linkContent(uri, Optional.empty());
	}

	@Override
	public MarkdomLinkContent linkContent(String uri, MarkdomContent... contents) {
		return linkContent(uri, Optional.empty()).addContents(contents);
	}

	@Override
	public MarkdomLinkContent linkContent(String uri, Iterable<MarkdomContent> contents) {
		return linkContent(uri, Optional.empty()).addContents(contents);
	}

	@Override
	public MarkdomLinkContent linkContent(String uri, Optional<String> title) {
		return new BasicMarkdomLinkContent(this, uri, title);
	}

	@Override
	public MarkdomLinkContent linkContent(String uri, Optional<String> title, MarkdomContent... contents) {
		return linkContent(uri, title).addContents(contents);
	}

	@Override
	public MarkdomLinkContent linkContent(String uri, Optional<String> title, Iterable<MarkdomContent> contents) {
		return linkContent(uri, title).addContents(contents);
	}

	@Override
	public MarkdomListItem listItem() {
		return new BasicMarkdomListItem(this);
	}

	@Override
	public MarkdomListItem listItem(MarkdomBlock... blocks) {
		return listItem().addBlocks(blocks);
	}

	@Override
	public MarkdomListItem listItem(Iterable<MarkdomBlock> blocks) {
		return listItem().addBlocks(blocks);
	}

	@Override
	public MarkdomOrderedListBlock orderedListBlock(Integer startIndex) {
		return new BasicMarkdomOrderedListBlock(this, startIndex);
	}

	@Override
	public MarkdomOrderedListBlock orderedListBlock(Integer startIndex, MarkdomListItem... listItems) {
		return orderedListBlock(startIndex).addListItems(listItems);
	}

	@Override
	public MarkdomOrderedListBlock orderedListBlock(Integer startIndex, Iterable<MarkdomListItem> listItems) {
		return orderedListBlock(startIndex).addListItems(listItems);
	}

	@Override
	public MarkdomParagraphBlock paragraphBlock() {
		return new BasicMarkdomParagraphBlock(this);
	}

	@Override
	public MarkdomParagraphBlock paragraphBlock(MarkdomContent... contents) {
		return paragraphBlock().addContents(contents);
	}

	@Override
	public MarkdomParagraphBlock paragraphBlock(Iterable<MarkdomContent> contents) {
		return paragraphBlock().addContents(contents);
	}

	@Override
	public MarkdomQuoteBlock quoteBlock() {
		return new BasicMarkdomQuoteBlock(this);
	}

	@Override
	public MarkdomQuoteBlock quoteBlock(MarkdomBlock... blocks) {
		return quoteBlock().addBlocks(blocks);
	}

	@Override
	public MarkdomQuoteBlock quoteBlock(Iterable<MarkdomBlock> blocks) {
		return quoteBlock().addBlocks(blocks);
	}

	@Override
	public MarkdomTextContent textContent(String text) {
		return new BasicMarkdomTextContent(this, text);
	}

	@Override
	public MarkdomUnorderedListBlock unorderedListBlock() {
		return new BasicMarkdomUnorderedListBlock(this);
	}

	@Override
	public MarkdomUnorderedListBlock unorderedListBlock(MarkdomListItem... listItems) {
		return unorderedListBlock().addListItems(listItems);
	}

	@Override
	public MarkdomUnorderedListBlock unorderedListBlock(Iterable<MarkdomListItem> listItems) {
		return unorderedListBlock().addListItems(listItems);
	}

}
