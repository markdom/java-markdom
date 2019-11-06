package io.markdom.handler.json.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.markdom.TestHelper;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class JacksonJsonModuleTest {

	@Test
	@SneakyThrows
	public void deserializeDocument() {

		MarkdomFactory factory = new BasicMarkdomFactory();

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JacksonMarkdomModule(factory));

		MarkdomDocument document = mapper.readValue(TestHelper.openExampleJson(), MarkdomDocument.class);

		assertEquals(TestHelper.getExampleDocument(factory), document);

	}

	@Test
	@SneakyThrows
	public void serializeDocument() {

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDocument document = TestHelper.getExampleDocument(factory);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JacksonMarkdomModule(factory));

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		mapper.writeValue(buffer, document);

		String json = new String(buffer.toByteArray(), Charset.forName("UTF-8"));

		JSONAssert.assertEquals(TestHelper.readExampleJson(), json, JSONCompareMode.STRICT_ORDER);

	}

}
