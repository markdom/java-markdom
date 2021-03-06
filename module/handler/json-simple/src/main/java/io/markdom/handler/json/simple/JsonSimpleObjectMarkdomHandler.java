package io.markdom.handler.json.simple;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import io.markdom.handler.json.AbstractJsonObjectMarkdomHandler;

public final class JsonSimpleObjectMarkdomHandler extends AbstractJsonObjectMarkdomHandler<JsonObject, JsonArray, JsonSimpleObjectResult> {

	public JsonSimpleObjectMarkdomHandler() {
		this(false);
	}

	public JsonSimpleObjectMarkdomHandler(boolean includeSchema) {
		super(includeSchema);
	}

	@Override
	protected JsonObject createObject() {
		return new JsonObject();
	}

	@Override
	protected JsonArray createArray() {
		return new JsonArray();
	}

	@Override
	protected void putBoolean(JsonObject object, String key, Boolean value) {
		object.put(key, value);
	}

	@Override
	protected void putInteger(JsonObject object, String key, Integer value) {
		object.put(key, value);
	}

	@Override
	protected void putString(JsonObject object, String key, String value) {
		object.put(key, value);
	}

	@Override
	protected void putArray(JsonObject object, String key, JsonArray value) {
		object.put(key, value);
	}

	@Override
	protected void addObject(JsonArray array, JsonObject object) {
		array.add(object);
	}

	@Override
	protected JsonSimpleObjectResult toResult(JsonObject object) {
		return new JsonSimpleObjectResult(object);
	}

}