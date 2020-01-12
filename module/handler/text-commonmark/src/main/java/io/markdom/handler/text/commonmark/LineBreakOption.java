package io.markdom.handler.text.commonmark;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum LineBreakOption {

	BACKSLASH("\\"),

	DOUBLE_SPACE("  ");

	@Getter
	private final String lineBreakString;

}
