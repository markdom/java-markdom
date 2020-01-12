package io.markdom.handler.json.gson;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import io.markdom.TestHelper;
import io.markdom.common.MarkdomExample;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class GsonJsonWriterMarkdomHandlerTests {

	@Test
	@SneakyThrows
	public void handleExampleObject() {

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDocument document = MarkdomExample.getExampleDocument(factory);

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		JsonWriter writer = new GsonBuilder().create().newJsonWriter(new OutputStreamWriter(buffer));

		document.handle(new GsonJsonWriterMarkdomHandler(writer));
		String json = new String(buffer.toByteArray(), Charset.forName("UTF-8"));

		assertEquals(TestHelper.readExampleJson(), json, JSONCompareMode.STRICT_ORDER);

	}

}
