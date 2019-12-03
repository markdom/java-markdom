package io.markdom.handler.text.commonmark;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonmarkTextOptions {

	private CodeBlockOption codeBlockOption = CodeBlockOption.FENCED;

	private CodeFenceOption codeFenceOption = CodeFenceOption.BACKTICK;

	private DivisionOption divisionOption = DivisionOption.DASH;

	private OrderedListOption orderedListOption = OrderedListOption.DOT;

	private UnorderedListOption unorderedListOption = UnorderedListOption.DASH;

	private EmphasisOption emphasisLevel1Option = EmphasisOption.STAR;

	private EmphasisOption emphasisLevel2Option = EmphasisOption.STAR;

	private LineBreakOption lineBreakOption = LineBreakOption.BACKSLASH;

	private LineEndOption lineEndOption = LineEndOption.UNIX;

}
