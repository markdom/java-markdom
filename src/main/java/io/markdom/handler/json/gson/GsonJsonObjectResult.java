package io.markdom.handler.json.gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import io.markdom.handler.json.JsonObjectResult;

public class GsonJsonObjectResult implements JsonObjectResult<JsonObject> {

	private final JsonObject object;

	public GsonJsonObjectResult(JsonObject object) {
		if (null == object) {
			throw new IllegalArgumentException("The given object is null");
		}
		this.object = object;
	}

	@Override
	public JsonObject asObject() {
		return object;
	}

	@Override
	public String asObjectText(boolean pretty) {
		GsonBuilder builder = new GsonBuilder();
		if (pretty) {
			builder.setPrettyPrinting();
		}
		return builder.create().toJson(object);
	}

}
