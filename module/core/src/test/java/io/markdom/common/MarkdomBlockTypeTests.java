package io.markdom.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MarkdomBlockTypeTests {

	@Test
	void markdomBlockTypeByKnownName() {

		MarkdomBlockType markdomBlockType = MarkdomBlockType.fromName(MarkdomBlockType.HEADING.toName());

		assertEquals(MarkdomBlockType.HEADING, markdomBlockType);

	}

	@Test
	void markdomBlockTypeByUnknownName() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			MarkdomBlockType.fromName("Unknown");
		});

	}

}
