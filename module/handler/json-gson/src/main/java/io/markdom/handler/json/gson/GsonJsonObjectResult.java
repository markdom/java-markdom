package io.markdom.handler.json.gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import io.markdom.handler.json.JsonObjectResult;
import io.markdom.util.ObjectHelper;

public final class GsonJsonObjectResult implements JsonObjectResult<JsonObject> {

	private final JsonObject object;

	public GsonJsonObjectResult(JsonObject object) {
		this.object = ObjectHelper.notNull("object", object);
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
