package io.markdom.handler.json.gson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.markdom.TestHelper;
import io.markdom.common.MarkdomExample;
import io.markdom.handler.MarkdomDispatcher;
import io.markdom.handler.MarkdomDocumentMarkdomHandler;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class GsonJsonObjectMarkdomDispatcherTests {

	@Test
	@SneakyThrows
	public void dispatchExampleDocument() {

		JsonObject jsonObject = JsonParser.parseReader(TestHelper.openExampleJson()).getAsJsonObject();

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDispatcher dispatcher = new GsonJsonObjectMarkdomDispatcher(jsonObject);
		MarkdomDocument document = dispatcher.handle(new MarkdomDocumentMarkdomHandler(factory));

		assertEquals(MarkdomExample.getExampleDocument(factory), document);

	}

}
