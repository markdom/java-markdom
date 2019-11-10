package io.markdom.handler.json.gson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.markdom.TestHelper;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import lombok.SneakyThrows;

public class GsonJsonDocumentModuleTest {

	@Test
	@SneakyThrows
	public void deserializeDocument() {

		MarkdomFactory factory = new BasicMarkdomFactory();

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeHierarchyAdapter(MarkdomDocument.class, new GsonMarkdomDocumentDeserializer(factory));
		Gson gson = builder.create();

		MarkdomDocument document = gson.fromJson(TestHelper.openExampleJson(), MarkdomDocument.class);

		assertEquals(TestHelper.getExampleDocument(factory), document);

	}

	@Test
	@SneakyThrows
	public void serializeDocument() {

		MarkdomFactory factory = new BasicMarkdomFactory();
		MarkdomDocument document = TestHelper.getExampleDocument(factory);

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeHierarchyAdapter(MarkdomDocument.class, new GsonMarkdomDocumentSerializer());
		Gson gson = builder.create();

		String json = gson.toJson(document);

		assertEquals(TestHelper.readExampleJson(), json, JSONCompareMode.STRICT_ORDER);

	}

}
