package io.markdom.common;

/**
 * A {@link MarkdomHeadingLevel} indicates the level of a <a href="http://markdom.io/#domain-headingblock"><i>Markdom
 * heading block</i></a>.
 */
public enum MarkdomHeadingLevel {

	/**
	 * Indication for a <a href="http://markdom.io/#domain-headingblock"><i>Markdom heading block</i></a> with level
	 * {@literal 1}.
	 */
	LEVEL_1,

	/**
	 * Indication for a <a href="http://markdom.io/#domain-headingblock"><i>Markdom heading block</i></a> with level
	 * {@literal 2}.
	 */
	LEVEL_2,

	/**
	 * Indication for a <a href="http://markdom.io/#domain-headingblock"><i>Markdom heading block</i></a> with level
	 * {@literal 3}.
	 */
	LEVEL_3,

	/**
	 * Indication for a <a href="http://markdom.io/#domain-headingblock"><i>Markdom heading block</i></a> with level
	 * {@literal 4}.
	 */
	LEVEL_4,

	/**
	 * Indication for a <a href="http://markdom.io/#domain-headingblock"><i>Markdom heading block</i></a> with level
	 * {@literal 5}.
	 */
	LEVEL_5,

	/**
	 * Indication for a <a href="http://markdom.io/#domain-headingblock"><i>Markdom heading block</i></a> with level
	 * {@literal 6}.
	 */
	LEVEL_6;

	/**
	 * Returns the integer value of this {@link MarkdomHeadingLevel}.
	 * 
	 * @return The integer level.
	 */
	public int toOrdinal() {
		return ordinal() + 1;
	}

	/**
	 * Returns the {@link MarkdomHeadingLevel} for the given integer value.
	 * 
	 * @param value The integer value to be used.
	 * @return The {@link MarkdomHeadingLevel}.
	 * @throws IllegalArgumentException If the given integer value is smaller than {@literal 1} or larger than {@literal 6}.
	 */
	public static MarkdomHeadingLevel fromOrdinal(int value) throws IllegalArgumentException {
		if (value < 1) {
			throw new IllegalArgumentException("The given integer value is smaller than '1': " + value);
		} else if (value > 2) {
			throw new IllegalArgumentException("The given integer value is larger than '6': " + value);
		}
		return values()[value - 1];
	}

}