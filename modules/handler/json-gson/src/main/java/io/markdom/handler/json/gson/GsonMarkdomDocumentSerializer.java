package io.markdom.handler.json.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import io.markdom.model.MarkdomDocument;

public final class GsonMarkdomDocumentSerializer implements JsonSerializer<MarkdomDocument> {

	private final boolean generateSchema;

	public GsonMarkdomDocumentSerializer() {
		this(false);
	}

	public GsonMarkdomDocumentSerializer(boolean generateSchema) {
		this.generateSchema = generateSchema;
	}

	@Override
	public JsonElement serialize(MarkdomDocument document, Type type, JsonSerializationContext context) {
		return document.handle(new GsonJsonObjectMarkdomHandler(generateSchema)).asObject();
	}

}
