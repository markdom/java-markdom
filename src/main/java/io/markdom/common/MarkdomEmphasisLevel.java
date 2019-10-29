package io.markdom.common;

/**
 * A {@link MarkdomContentType} indicates the level of a <a href="http://markdom.io/#domain-emphasiscontent"><i>Markdom
 * emphasis content</i></a>.
 */
public enum MarkdomEmphasisLevel {

	/**
	 * Indication for a <a href="http://markdom.io/#domain-emphasiscontent"><i>Markdom emphasis content</i></a> with level
	 * {@literal 1}.
	 */
	LEVEL_1,

	/**
	 * Indication for a <a href="http://markdom.io/#domain-emphasiscontent"><i>Markdom emphasis content</i></a> with level
	 * {@literal 2}.
	 */
	LEVEL_2;

	/**
	 * Returns the integer value of this {@link MarkdomEmphasisLevel}.
	 * 
	 * @return The integer level.
	 */
	public int toOrdinal() {
		return ordinal() + 1;
	}

	/**
	 * Returns the {@link MarkdomEmphasisLevel} for the given integer value.
	 * 
	 * @param value The integer value to be used.
	 * @return The {@link MarkdomEmphasisLevel}.
	 * @throws IllegalArgumentException If the given integer value is smaller than {@literal 1} or larger than {@literal 2}.
	 */
	public static MarkdomEmphasisLevel fromOrdinal(int value) throws IllegalArgumentException {
		if (value < 1) {
			throw new IllegalArgumentException("The given integer value is smaller than '1': " + value);
		} else if (value > 2) {
			throw new IllegalArgumentException("The given integer value is larger than '2': " + value);
		}
		return values()[value - 1];
	}

}