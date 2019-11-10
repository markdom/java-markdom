package io.markdom.common;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MarkdomContentType {

	CODE("Code"),

	EMPHASIS("Emphasis"),

	IMAGE("Image"),

	LINE_BREAK("LineBreak"),

	LINK("Link"),

	TEXT("Text");

	private static final Map<String, MarkdomContentType> MAP = new HashMap<String, MarkdomContentType>();

	static {
		for (MarkdomContentType type : values()) {
			MAP.put(type.name, type);
		}
	}

	private final String name;

	public String toName() {
		return name;
	}

	public static MarkdomContentType fromName(String value) throws IllegalArgumentException {
		MarkdomContentType type = MAP.get(value);
		if (null == type) {
			throw new IllegalArgumentException("There is no content type with the given string value: " + value);
		} else {
			return type;
		}
	}

}