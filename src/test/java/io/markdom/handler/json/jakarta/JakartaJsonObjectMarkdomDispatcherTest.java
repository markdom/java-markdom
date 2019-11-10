package io.markdom.handler.json.jakarta;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.jupiter.api.Test;

import io.markdom.TestHelper;
import io.markdom.handler.MarkdomDispatcher;
import io.markdom.handler.MarkdomDocumentMarkdomHandler;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class JakartaJsonObjectMarkdomDispatcherTest {

	@Test
	@SneakyThrows
	public void dispatchExampleDocument() {

		JsonObject jsonObject = Json.createReader(TestHelper.openExampleJson()).readObject();

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDispatcher dispatcher = new JakartaJsonObjectMarkdomDispatcher(jsonObject);
		MarkdomDocument document = dispatcher.handle(new MarkdomDocumentMarkdomHandler(factory));

		assertEquals(TestHelper.getExampleDocument(factory), document);

	}

}
