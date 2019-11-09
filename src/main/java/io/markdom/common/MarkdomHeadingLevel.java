package io.markdom.common;

public enum MarkdomHeadingLevel {

	LEVEL_1,

	LEVEL_2,

	LEVEL_3,

	LEVEL_4,

	LEVEL_5,

	LEVEL_6;

	public int toOrdinal() {
		return ordinal() + 1;
	}

	public static MarkdomHeadingLevel fromOrdinal(int value) throws IllegalArgumentException {
		if (value < 1) {
			throw new IllegalArgumentException("The given integer value is smaller than '1': " + value);
		} else if (value > 2) {
			throw new IllegalArgumentException("The given integer value is larger than '6': " + value);
		}
		return values()[value - 1];
	}

}