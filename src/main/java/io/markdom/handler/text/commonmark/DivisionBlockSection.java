package io.markdom.handler.text.commonmark;

import io.markdom.util.StringUtil;

final class DivisionBlockSection implements Section {

	private final String divisionString;

	DivisionBlockSection(CommonmarkTextOptions options) {
		divisionString = StringUtil.repeat(options.getDivisionOption().getDivisionCharacter(), 3);
	}

	@Override
	public void appendTo(LineAppendable sink) {
		sink.startLine();
		sink.append(divisionString);
		sink.endLine();
	}

}