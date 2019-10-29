package io.markdom.common;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;

/**
 * A {@link MarkdomBlockType} indicates the type of a <a href="http://markdom.io/#domain-block"><i>Markdom
 * block</i></a>.
 */
@RequiredArgsConstructor
public enum MarkdomBlockType {

	/**
	 * Indication for a <a href="http://markdom.io/#domain-codeblock"><i>Markdom code block</i></a>.
	 */
	CODE("Code"),

	/**
	 * Indication for a <a href="http://markdom.io/#domain-commentblock"><i>Markdom comment block</i></a>.
	 */
	COMMENT("Comment"),

	/**
	 * Indication for a <a href="http://markdom.io/#domain-divisionblock"><i>Markdom division block</i></a>.
	 */
	DIVISION("Division"),

	/**
	 * Indication for a <a href="http://markdom.io/#domain-headingblock"><i>Markdom heading block</i></a>.
	 */
	HEADING("Heading"),

	/**
	 * Indication for an <a href="http://markdom.io/#domain-orderedlistblock"><i>ordered Markdom list block</i></a>.
	 */
	ORDERED_LIST("OrderedList"),

	/**
	 * Indication for a <a href="http://markdom.io/#domain-paragraphblock"><i>Markdom paragraph block</i></a>.
	 */
	PARAGRAPH("Paragraph"),

	/**
	 * Indication for a <a href="http://markdom.io/#domain-quoteblock"><i>Markdom quote block</i></a>.
	 */
	QUOTE("Quote"),

	/**
	 * Indication for an <a href="http://markdom.io/#domain-unorderedlistblock"><i>unordered Markdom code block</i></a>.
	 */
	UNORDERED_LIST("UnorderedList");

	private static final Map<String, MarkdomBlockType> MAP = new HashMap<String, MarkdomBlockType>();

	static {
		for (MarkdomBlockType type : values()) {
			MAP.put(type.name, type);
		}
	}

	private final String name;

	/**
	 * Returns the name of this {@link MarkdomBlockType}.
	 * 
	 * @return The name of this {@link MarkdomBlockType}.
	 */
	public String toName() {
		return name;
	}

	/**
	 * Returns the {@link MarkdomBlockType} for the given name.
	 * 
	 * @param value The name.
	 * @return The {@link MarkdomBlockType}.
	 * @throws IllegalArgumentException If the is no {@link MarkdomBlockType} with the given name.
	 */
	public static MarkdomBlockType fromName(String value) throws IllegalArgumentException {
		MarkdomBlockType type = MAP.get(value);
		if (null == type) {
			throw new IllegalArgumentException("There is no Markdom block type with the given string value: " + value);
		} else {
			return type;
		}
	}

}