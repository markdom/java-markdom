package io.markdom.handler.text.commonmark;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OrderedListOption {

	DOT('.'),

	PARENTHESIS(')');

	@Getter
	private final char commitCharacter;

}
