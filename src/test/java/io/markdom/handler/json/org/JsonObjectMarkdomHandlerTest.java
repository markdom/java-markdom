package io.markdom.handler.json.org;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

import io.markdom.TestHelper;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class JsonObjectMarkdomHandlerTest {

	@Test
	@SneakyThrows
	public void handleExampleObject() {

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDocument document = TestHelper.getExampleDocument(factory);

		JSONObject jsonObject = document.handle(new JsonObjectMarkdomHandler());
		String json = jsonObject.toString();

		assertEquals(TestHelper.readExampleJson(), json, JSONCompareMode.STRICT_ORDER);

	}

}
