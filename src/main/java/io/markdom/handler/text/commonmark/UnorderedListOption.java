package io.markdom.handler.text.commonmark;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UnorderedListOption {

	DASH('-'),

	PLUS('+'),

	STAR('*');

	@Getter
	private final char bulletCharacter;

}
