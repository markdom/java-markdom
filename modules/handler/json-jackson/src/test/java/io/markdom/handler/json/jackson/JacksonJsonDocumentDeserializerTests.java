package io.markdom.handler.json.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.markdom.TestHelper;
import io.markdom.common.MarkdomExample;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

public class JacksonJsonDocumentDeserializerTests {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	private static class Holder {

		@JsonDeserialize(using = JacksonMarkdomDocumentDeserializer.class)
		private MarkdomDocument document;

	}

	@Test
	@SneakyThrows
	public void deserializeDocument() {

		MarkdomFactory factory = new BasicMarkdomFactory();

		ObjectMapper mapper = new ObjectMapper();

		MarkdomDocument document = mapper.readValue(wrappedExampleDocument(), Holder.class).getDocument();

		assertEquals(MarkdomExample.getExampleDocument(factory), document);

	}

	private String wrappedExampleDocument() {
		return "{\"document\":" + TestHelper.readExampleJson() + "}";
	}

}
