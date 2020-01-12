package io.markdom.handler.text.commonmark;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class CommonmarkTextConfiguration {

	@Data
	@Accessors(fluent = true)
	public static class Builder {

		private CodeBlockOption codeBlockOption = CodeBlockOption.FENCED;

		private CodeFenceOption codeFenceOption = CodeFenceOption.BACKTICK;

		private String adjacentIndentedCodeBlocksComment = "adjacent code blocks";

		private String emptyIndentedCodeBlockComment = "empty code block";

		private DivisionOption divisionOption = DivisionOption.DASH;

		private String leadingDivisionInListItemComment = "leading division in list item";

		private OrderedListOption orderedListOption = OrderedListOption.DOT;

		private UnorderedListOption unorderedListOption = UnorderedListOption.STAR;

		private String adjacentListBlocksComment = "adjacent list blocks";

		private String emptyListItemComment = "empty list item";

		private EmphasisOption emphasisLevel1Option = EmphasisOption.STAR;

		private EmphasisOption emphasisLevel2Option = EmphasisOption.STAR;

		private LineBreakOption lineBreakOption = LineBreakOption.BACKSLASH;

		private LineEndOption lineEndOption = LineEndOption.UNIX;

		public CommonmarkTextConfiguration build() {
			// @formatter:off
			return new CommonmarkTextConfiguration(
				codeBlockOption,
				codeFenceOption,
				adjacentIndentedCodeBlocksComment,
				emptyIndentedCodeBlockComment,
				divisionOption,
				leadingDivisionInListItemComment,
				orderedListOption,
				unorderedListOption,
				adjacentListBlocksComment,
				emptyListItemComment,
				emphasisLevel1Option,
				emphasisLevel2Option,
				lineBreakOption,
				lineEndOption
			);
			// @formatter:on
		}

	}

	private final CodeBlockOption codeBlockOption;

	private final CodeFenceOption codeFenceOption;

	private final String adjacentIndentedCodeBlockComment;

	private final String emptyIndentedCodeBlockComment;

	private final DivisionOption divisionOption;

	private final String leadingDivisionInListItemComment;

	private final OrderedListOption orderedListOption;

	private final UnorderedListOption unorderedListOption;

	private final String adjacentListBlocksComment;

	private final String emptyListItemComment;

	private final EmphasisOption emphasisLevel1Option;

	private final EmphasisOption emphasisLevel2Option;

	private final LineBreakOption lineBreakOption;

	private final LineEndOption lineEndOption;

	public static Builder builder() {
		return new Builder();
	}

}
