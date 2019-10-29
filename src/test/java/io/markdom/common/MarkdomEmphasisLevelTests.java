package io.markdom.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MarkdomEmphasisLevelTests {

	@Test
	void markdomEmphasisLevelByLegalOrdinal() {

		MarkdomEmphasisLevel markdomEmphasisLevel = MarkdomEmphasisLevel.fromOrdinal(MarkdomEmphasisLevel.LEVEL_1.toOrdinal());

		assertEquals(MarkdomEmphasisLevel.LEVEL_1, markdomEmphasisLevel);

	}

	@Test
	void markdomEmphasisLevelByTooSmallOrdinal() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			MarkdomEmphasisLevel.fromOrdinal(MarkdomEmphasisLevel.LEVEL_1.toOrdinal() - 1);
		});

	}

	@Test
	void markdomEmphasisLevelByTooLargeOrdinal() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			MarkdomEmphasisLevel.fromOrdinal(MarkdomEmphasisLevel.LEVEL_2.toOrdinal() + 1);
		});

	}

}
