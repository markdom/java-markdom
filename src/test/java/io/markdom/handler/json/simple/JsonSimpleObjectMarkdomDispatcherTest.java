package io.markdom.handler.json.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import io.markdom.TestHelper;
import io.markdom.handler.MarkdomDispatcher;
import io.markdom.handler.MarkdomDocumentMarkdomHandler;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class JsonSimpleObjectMarkdomDispatcherTest {

	@Test
	@SneakyThrows
	public void dispatchExampleDocument() {

		JsonObject jsonObject = (JsonObject) Jsoner.deserialize(TestHelper.openExampleJson());

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDispatcher dispatcher = new JsonSimpleObjectMarkdomDispatcher(jsonObject);
		MarkdomDocument document = dispatcher.handle(new MarkdomDocumentMarkdomHandler(factory));

		assertEquals(TestHelper.getExampleDocument(factory), document);

	}

}
