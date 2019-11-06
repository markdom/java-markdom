package io.markdom.handler.json.simple;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.github.cliftonlabs.json_simple.JsonObject;

import io.markdom.TestHelper;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class JsonSimpleObjectMarkdomHandlerTest {

	@Test
	@SneakyThrows
	public void handleExampleObject() {

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDocument document = TestHelper.getExampleDocument(factory);

		JsonObject jsonObject = document.handle(new JsonSimpleObjectMarkdomHandler());
		String json = jsonObject.toJson();

		assertEquals(TestHelper.readExampleJson(), json, JSONCompareMode.STRICT_ORDER);

	}

}
