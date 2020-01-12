package io.markdom.handler.text.commonmark;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DivisionOption {

	STAR('*'),

	DASH('-'),

	UNDERSCORE('_');

	@Getter
	private final char divisionCharacter;

}
