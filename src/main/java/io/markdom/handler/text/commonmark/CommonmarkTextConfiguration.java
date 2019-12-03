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

		private String emptyIndentedCodeBlockComment = "empty code block";

		private DivisionOption divisionOption = DivisionOption.DASH;

		private OrderedListOption orderedListOption = OrderedListOption.DOT;

		private UnorderedListOption unorderedListOption = UnorderedListOption.DASH;

		private String adjacentListBlocksComment = "adjacent list blocks";

		private EmphasisOption emphasisLevel1Option = EmphasisOption.STAR;

		private EmphasisOption emphasisLevel2Option = EmphasisOption.STAR;

		private LineBreakOption lineBreakOption = LineBreakOption.BACKSLASH;

		private LineEndOption lineEndOption = LineEndOption.UNIX;

		public CommonmarkTextConfiguration build() {
			// @formatter:off
			return new CommonmarkTextConfiguration(
				codeBlockOption,
				codeFenceOption,
				emptyIndentedCodeBlockComment,
				divisionOption,
				orderedListOption,
				unorderedListOption,
				adjacentListBlocksComment,
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

	private final String emptyIndentedCodeBlockComment;

	private final DivisionOption divisionOption;

	private final OrderedListOption orderedListOption;

	private final UnorderedListOption unorderedListOption;

	private final String adjacentListBlocksComment;

	private final EmphasisOption emphasisLevel1Option;

	private final EmphasisOption emphasisLevel2Option;

	private final LineBreakOption lineBreakOption;

	private final LineEndOption lineEndOption;

	public static Builder builder() {
		return new Builder();
	}

}
