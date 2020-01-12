package io.markdom.handler.json.jakarta;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

import io.markdom.handler.json.JsonObjectResult;
import io.markdom.util.ObjectHelper;

public final class JakartaJsonObjectResult implements JsonObjectResult<JsonObject> {

	private final JsonObject object;

	public JakartaJsonObjectResult(JsonObject object) {
		this.object = ObjectHelper.notNull("object", object);
	}

	@Override
	public JsonObject asObject() {
		return object;
	}

	@Override
	public String asObjectText(boolean pretty) {

		Map<String, Object> properties = new HashMap<>(1);
		if (pretty) {
			properties.put(JsonGenerator.PRETTY_PRINTING, true);
		}

		StringWriter writer = new StringWriter();
		JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
		JsonWriter jsonWriter = writerFactory.createWriter(writer);
		jsonWriter.writeObject(object);
		jsonWriter.close();

		return writer.toString();

	}

}
