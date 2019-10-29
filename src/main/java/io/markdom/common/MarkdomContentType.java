package io.markdom.common;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;

/**
 * A {@link MarkdomContentType} indicates the type of a <a href="http://markdom.io/#domain-content"><i>Markdom
 * content</i></a>.
 */
@RequiredArgsConstructor
public enum MarkdomContentType {

	/**
	 * Indication for a <a href="http://markdom.io/#domain-codecontent"><i>Markdom code content</i></a>
	 */
	CODE("Code"),

	/**
	 * Indication for a <a href="http://markdom.io/#domain-emphasiscontent"><i>Markdom emphasis content</i></a>
	 */
	EMPHASIS("Emphasis"),

	/**
	 * Indication for a <a href="http://markdom.io/#domain-imageontent"><i>Markdom image content</i></a>
	 */
	IMAGE("Image"),

	/**
	 * Indication for a <a href="http://markdom.io/#domain-linebreakcontent"><i>Markdom line break content</i></a>
	 */
	LINE_BREAK("LineBreak"),

	/**
	 * Indication for a <a href="http://markdom.io/#domain-linkcontent"><i>Markdom link content</i></a>
	 */
	LINK("Link"),

	/**
	 * Indication for a <a href="http://markdom.io/#domain-textcontent"><i>Markdom text content</i></a>
	 */
	TEXT("Text");

	private static final Map<String, MarkdomContentType> MAP = new HashMap<String, MarkdomContentType>();

	static {
		for (MarkdomContentType type : values()) {
			MAP.put(type.name, type);
		}
	}

	private final String name;

	/**
	 * Returns the name of this {@link MarkdomContentType}.
	 * 
	 * @return The name of this {@link MarkdomContentType}.
	 */
	public String toName() {
		return name;
	}

	/**
	 * Returns the {@link MarkdomContentType} for the given name.
	 * 
	 * @param value The name.
	 * @return The {@link MarkdomContentType}.
	 * @throws IllegalArgumentException If the is no {@link MarkdomContentType} with the given name.
	 */
	public static MarkdomContentType fromName(String value) throws IllegalArgumentException {
		MarkdomContentType type = MAP.get(value);
		if (null == type) {
			throw new IllegalArgumentException("There is no Markdom content type with the given string value: " + value);
		} else {
			return type;
		}
	}

}