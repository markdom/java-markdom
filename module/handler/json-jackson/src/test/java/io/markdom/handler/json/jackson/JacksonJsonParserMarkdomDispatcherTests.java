package io.markdom.handler.json.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.markdom.TestHelper;
import io.markdom.common.MarkdomExample;
import io.markdom.handler.MarkdomDispatcher;
import io.markdom.handler.MarkdomDocumentMarkdomHandler;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class JacksonJsonParserMarkdomDispatcherTests {

	@Test
	@SneakyThrows
	public void dispatchExampleDocument() {

		ObjectNode jsonObject = new JsonFactory(new ObjectMapper()).createParser(TestHelper.openExampleJson()).readValueAsTree();

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDispatcher dispatcher = new JacksonJsonObjectMarkdomDispatcher(jsonObject);
		MarkdomDocument document = dispatcher.handle(new MarkdomDocumentMarkdomHandler(factory));

		assertEquals(MarkdomExample.getExampleDocument(factory), document);

	}

}
