package io.markdom.util;

import lombok.Data;

@Data
public final class Attribute {

	private final String key;

	private final String value;

	public Attribute(String key, String value) {
		this.key = ObjectHelper.notNull("key", key);
		this.value = ObjectHelper.notNull("value", value);
	}

}