package io.markdom.handler.text.commonmark;

import lombok.Getter;

class ParagraphSection implements ContentSection {

	@Getter
	private final ContentBuffer buffer;

	ParagraphSection(CommonmarkTextOptions options) {
		this.buffer = new ContentBuffer(options, LineBreakOption.BACKSLASH.getLineBreakString());
	}

	@Override
	public void appendTo(LineAppendable sink) {
		buffer.appendTo(sink);
	}

}
