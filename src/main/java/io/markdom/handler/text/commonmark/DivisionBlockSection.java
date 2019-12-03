package io.markdom.handler.text.commonmark;

import io.markdom.util.StringUtil;

final class DivisionBlockSection implements Section {

	private final String divisionString;

	DivisionBlockSection(CommonmarkTextConfiguration configuration) {
		divisionString = StringUtil.repeat(configuration.getDivisionOption().getDivisionCharacter(), 3);
	}

	@Override
	public void appendTo(LineAppendable appendable) {
		appendable.startLine();
		appendable.append(divisionString);
		appendable.endLine();
	}

}