package io.markdom.handler.text.commonmark;

import lombok.Getter;

class ParagraphSection implements ContentSection {

	@Getter
	private final ContentBuffer buffer;

	ParagraphSection(CommonmarkTextConfiguration configuration) {
		this.buffer = new ContentBuffer(configuration, LineBreakOption.BACKSLASH.getLineBreakString());
	}

	@Override
	public void appendTo(LineAppendable appendable) {
		buffer.appendTo(appendable);
	}

}
