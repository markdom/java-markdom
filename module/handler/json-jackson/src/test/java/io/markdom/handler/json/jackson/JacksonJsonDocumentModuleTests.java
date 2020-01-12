package io.markdom.handler.json.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.markdom.TestHelper;
import io.markdom.common.MarkdomExample;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class JacksonJsonDocumentModuleTests {

	@Test
	@SneakyThrows
	public void deserializeDocument() {

		MarkdomFactory factory = new BasicMarkdomFactory();

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JacksonMarkdomDocumentModule(factory));

		MarkdomDocument document = mapper.readValue(TestHelper.openExampleJson(), MarkdomDocument.class);

		assertEquals(MarkdomExample.getExampleDocument(factory), document);

	}

	@Test
	@SneakyThrows
	public void serializeDocument() {

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDocument document = MarkdomExample.getExampleDocument(factory);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JacksonMarkdomDocumentModule(factory));

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		mapper.writeValue(buffer, document);

		String json = new String(buffer.toByteArray(), Charset.forName("UTF-8"));

		assertEquals(TestHelper.readExampleJson(), json, JSONCompareMode.STRICT_ORDER);

	}

}
