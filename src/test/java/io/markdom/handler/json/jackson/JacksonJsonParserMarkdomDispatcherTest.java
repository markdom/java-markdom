package io.markdom.handler.json.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.markdom.TestHelper;
import io.markdom.handler.MarkdomDispatcher;
import io.markdom.handler.MarkdomDocumentMarkdomHandler;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class JacksonJsonParserMarkdomDispatcherTest {

	@Test
	@SneakyThrows
	public void dispatchExampleDocument() {

		JsonParser parser = new JsonFactory(new ObjectMapper()).createParser(TestHelper.openExampleJson());

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDispatcher dispatcher = new JacksonJsonParserMarkdomDispatcher(parser);
		MarkdomHandler<MarkdomDocument> handler = new MarkdomDocumentMarkdomHandler(factory);

		MarkdomDocument document = dispatcher.handle(handler);

		assertEquals(TestHelper.getExampleDocument(factory), document);

	}

}
