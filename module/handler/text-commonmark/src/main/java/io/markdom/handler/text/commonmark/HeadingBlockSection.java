package io.markdom.handler.text.commonmark;

import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.StringUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class HeadingBlockSection implements ContentSection {

	@Getter
	private final ContentBuffer buffer;

	private final String levelString;

	public HeadingBlockSection(CommonmarkTextConfiguration configuration, MarkdomHeadingLevel level) {
		this.levelString = StringUtil.repeat('#', level.ordinal() + 1) + " ";
		this.buffer = new ContentBuffer(configuration, "");
	}

	@Override
	public void appendTo(LineAppendable appendable) {
		appendable.startLine();
		appendable.append(levelString);
		buffer.appendTo(new HeadingAppendable(appendable, " "));
		appendable.endLine();
	}

}
