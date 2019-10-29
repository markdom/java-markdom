package io.markdom.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MarkdomContentTypeTests {

	@Test
	void markdomContentTypeByKnownName() {

		MarkdomContentType markdomContentType = MarkdomContentType.fromName(MarkdomContentType.TEXT.toName());

		assertEquals(MarkdomContentType.TEXT, markdomContentType);

	}

	@Test
	void markdomContentTypeByUnknownName() {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			MarkdomContentType.fromName("Unknown");
		});

	}

}
