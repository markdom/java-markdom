package io.markdom.handler.text.commonmark;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CodeFenceOption {

	BACKTICK('`'),

	TILDE('~');

	@Getter
	private final char fenceCharacter;

}
