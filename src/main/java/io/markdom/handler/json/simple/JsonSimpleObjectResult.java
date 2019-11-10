package io.markdom.handler.json.simple;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import io.markdom.handler.json.JsonObjectResult;

public class JsonSimpleObjectResult implements JsonObjectResult<JsonObject> {

	private final JsonObject object;

	public JsonSimpleObjectResult(JsonObject object) {
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
		return pretty ? Jsoner.prettyPrint(object.toJson()) : object.toJson();
	}

}
