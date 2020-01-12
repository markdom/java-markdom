package io.markdom.handler.text.commonmark;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Separation {

	EMPTY_LINE(Optional.of("")),

	COMMENT(Optional.of("<!-- -->")),

	NONE(Optional.empty());

	@Getter
	private final Optional<String> separationString;

}
