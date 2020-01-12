package io.markdom.handler.json.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.markdom.handler.json.AbstractJsonObjectMarkdomHandler;

public final class GsonJsonObjectMarkdomHandler extends AbstractJsonObjectMarkdomHandler<JsonObject, JsonArray, GsonJsonObjectResult> {

	public GsonJsonObjectMarkdomHandler() {
		this(false);
	}

	public GsonJsonObjectMarkdomHandler(boolean includeSchema) {
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
		object.add(key, new JsonPrimitive(value));
	}

	@Override
	protected void putInteger(JsonObject object, String key, Integer value) {
		object.add(key, new JsonPrimitive(value));
	}

	@Override
	protected void putString(JsonObject object, String key, String value) {
		object.add(key, new JsonPrimitive(value));
	}

	@Override
	protected void putArray(JsonObject object, String key, JsonArray value) {
		object.add(key, value);
	}

	@Override
	protected void addObject(JsonArray array, JsonObject object) {
		array.add(object);
	}

	@Override
	protected GsonJsonObjectResult toResult(JsonObject object) {
		return new GsonJsonObjectResult(object);
	}

}