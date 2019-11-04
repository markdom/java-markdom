package io.markdom.model;

import java.util.Optional;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;

public interface MarkdomFactory {

	public MarkdomCodeBlock codeBlock(String code);

	public MarkdomCodeBlock codeBlock(String code, Optional<String> hint);

	public MarkdomCodeContent codeContent(String code);

	public MarkdomCommentBlock commentBlock(String comment);

	public MarkdomDivisionBlock divisionBlock();

	public MarkdomDocument document();

	public MarkdomDocument document(MarkdomBlock block);

	public MarkdomDocument document(MarkdomBlock... blocks);

	public MarkdomDocument document(Iterable<MarkdomBlock> blocks);

	public MarkdomEmphasisContent emphasisContent(MarkdomEmphasisLevel level);

	public MarkdomEmphasisContent emphasisContent(MarkdomEmphasisLevel level, MarkdomContent content);

	public MarkdomEmphasisContent emphasisContent(MarkdomEmphasisLevel level, MarkdomContent... contents);

	public MarkdomEmphasisContent emphasisContent(MarkdomEmphasisLevel level, Iterable<MarkdomContent> contents);

	public MarkdomHeadingBlock headingBlock(MarkdomHeadingLevel level);

	public MarkdomHeadingBlock headingBlock(MarkdomHeadingLevel level, MarkdomContent content);

	public MarkdomHeadingBlock headingBlock(MarkdomHeadingLevel level, MarkdomContent... contents);

	public MarkdomHeadingBlock headingBlock(MarkdomHeadingLevel level, Iterable<MarkdomContent> contents);

	public MarkdomImageContent imageContent(String uri);

	public MarkdomImageContent imageContent(String uri, Optional<String> title, Optional<String> alternative);

	public MarkdomLineBreakContent lineBreakContent(Boolean hard);

	public MarkdomLinkContent linkContent(String uri);

	public MarkdomLinkContent linkContent(String uri, MarkdomContent content);

	public MarkdomLinkContent linkContent(String uri, MarkdomContent... contents);

	public MarkdomLinkContent linkContent(String uri, Iterable<MarkdomContent> contents);

	public MarkdomLinkContent linkContent(String uri, Optional<String> title);

	public MarkdomLinkContent linkContent(String uri, Optional<String> title, MarkdomContent content);

	public MarkdomLinkContent linkContent(String uri, Optional<String> title, MarkdomContent... contents);

	public MarkdomLinkContent linkContent(String uri, Optional<String> title, Iterable<MarkdomContent> contents);

	public MarkdomListItem listItem();

	public MarkdomListItem listItem(MarkdomBlock block);

	public MarkdomListItem listItem(MarkdomBlock... blocks);

	public MarkdomListItem listItem(Iterable<MarkdomBlock> blocks);

	public MarkdomOrderedListBlock orderedListBlock(Integer startIndex);

	public MarkdomOrderedListBlock orderedListBlock(Integer startIndex, MarkdomListItem listItem);

	public MarkdomOrderedListBlock orderedListBlock(Integer startIndex, MarkdomListItem... listItems);

	public MarkdomOrderedListBlock orderedListBlock(Integer startIndex, Iterable<MarkdomListItem> listItems);

	public MarkdomParagraphBlock paragraphBlock();

	public MarkdomParagraphBlock paragraphBlock(MarkdomContent content);

	public MarkdomParagraphBlock paragraphBlock(MarkdomContent... contents);

	public MarkdomParagraphBlock paragraphBlock(Iterable<MarkdomContent> contents);

	public MarkdomQuoteBlock quoteBlock();

	public MarkdomQuoteBlock quoteBlock(MarkdomBlock block);

	public MarkdomQuoteBlock quoteBlock(MarkdomBlock... blocks);

	public MarkdomQuoteBlock quoteBlock(Iterable<MarkdomBlock> blocks);

	public MarkdomTextContent textContent(String text);

	public MarkdomUnorderedListBlock unorderedListBlock();

	public MarkdomUnorderedListBlock unorderedListBlock(MarkdomListItem listItem);

	public MarkdomUnorderedListBlock unorderedListBlock(MarkdomListItem... listItems);

	public MarkdomUnorderedListBlock unorderedListBlock(Iterable<MarkdomListItem> listItems);

}
