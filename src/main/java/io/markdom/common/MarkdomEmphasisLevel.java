package io.markdom.common;

public enum MarkdomEmphasisLevel {

	LEVEL_1,

	LEVEL_2;

	public int toOrdinal() {
		return ordinal() + 1;
	}

	public static MarkdomEmphasisLevel fromOrdinal(int value) throws IllegalArgumentException {
		if (value < 1) {
			throw new IllegalArgumentException("The given integer value is smaller than '1': " + value);
		} else if (value > 2) {
			throw new IllegalArgumentException("The given integer value is larger than '2': " + value);
		}
		return values()[value - 1];
	}

}