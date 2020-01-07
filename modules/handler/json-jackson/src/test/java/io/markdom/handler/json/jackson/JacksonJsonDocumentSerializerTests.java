package io.markdom.handler.json.jackson;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.markdom.TestHelper;
import io.markdom.common.MarkdomExample;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

public class JacksonJsonDocumentSerializerTests {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	private static class Holder {

		@JsonSerialize(using = JacksonMarkdomDocumentSerializer.class)
		private MarkdomDocument document;

	}

	@Test
	@SneakyThrows
	public void handleExampleObject() {

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDocument document = MarkdomExample.getExampleDocument(factory);

		ObjectMapper mapper = new ObjectMapper();

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		mapper.writeValue(buffer, new Holder(document));

		String json = new String(buffer.toByteArray(), Charset.forName("UTF-8"));

		assertEquals(wrappedExampleDocument(), json, JSONCompareMode.STRICT_ORDER);

	}

	private String wrappedExampleDocument() {
		return "{\"document\":" + TestHelper.readExampleJson() + "}";
	}

}
