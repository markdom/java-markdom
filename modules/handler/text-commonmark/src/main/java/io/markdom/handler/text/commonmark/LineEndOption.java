package io.markdom.handler.text.commonmark;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum LineEndOption {

	UNIX("\n"),

	WINDOWS("\r\n");

	@Getter
	private final String lineEndString;

}
