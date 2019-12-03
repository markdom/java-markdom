package io.markdom.handler.text.commonmark;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EmphasisOption {

	STAR('*'),

	UNDERSCORE('_');

	@Getter
	private final char bounderyCharacter;

}
