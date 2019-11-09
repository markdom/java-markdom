package io.markdom.common;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MarkdomBlockType {

	CODE("Code"),

	COMMENT("Comment"),

	DIVISION("Division"),

	HEADING("Heading"),

	ORDERED_LIST("OrderedList"),

	PARAGRAPH("Paragraph"),

	QUOTE("Quote"),

	UNORDERED_LIST("UnorderedList");

	private static final Map<String, MarkdomBlockType> MAP = new HashMap<String, MarkdomBlockType>();

	static {
		for (MarkdomBlockType type : values()) {
			MAP.put(type.name, type);
		}
	}

	private final String name;

	public String toName() {
		return name;
	}

	public static MarkdomBlockType fromName(String value) throws IllegalArgumentException {
		MarkdomBlockType type = MAP.get(value);
		if (null == type) {
			throw new IllegalArgumentException("There is no Markdom block type with the given string value: " + value);
		} else {
			return type;
		}
	}

}