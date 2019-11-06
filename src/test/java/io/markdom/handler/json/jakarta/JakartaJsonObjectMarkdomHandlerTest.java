package io.markdom.handler.json.jakarta;

import javax.json.JsonObject;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import io.markdom.TestHelper;
import io.markdom.handler.json.jakarta.JakartaJsonObjectMarkdomHandler;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class JakartaJsonObjectMarkdomHandlerTest {

	@Test
	@SneakyThrows
	public void handleExampleObject() {

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDocument document = TestHelper.getExampleDocument(factory);

		JsonObject jsonObject = document.handle(new JakartaJsonObjectMarkdomHandler());
		String json = jsonObject.toString();

		JSONAssert.assertEquals(TestHelper.readExampleJson(), json, JSONCompareMode.STRICT_ORDER);

	}

}
