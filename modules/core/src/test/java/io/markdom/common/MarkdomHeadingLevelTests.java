package io.markdom.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MarkdomHeadingLevelTests {

	@Test
	void markdomHeadingLevelByLegalOrdinal() {

		MarkdomHeadingLevel markdomHeadingLevel = MarkdomHeadingLevel.fromOrdinal(MarkdomHeadingLevel.LEVEL_1.toOrdinal());

		assertEquals(MarkdomHeadingLevel.LEVEL_1, markdomHeadingLevel);

	}

	@Test
	void markdomHeadingLevelByTooSmallOrdinal() {
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			MarkdomHeadingLevel.fromOrdinal(MarkdomHeadingLevel.LEVEL_1.toOrdinal() - 1);
		});

	}

	@Test
	void markdomHeadingLevelByTooLargeOrdinal() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			MarkdomHeadingLevel.fromOrdinal(MarkdomHeadingLevel.LEVEL_6.toOrdinal() + 1);
		});

	}

}
