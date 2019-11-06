package io.markdom.handler.json.jakarta;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import io.markdom.handler.json.AbstractJsonObjectMarkdomHandler;

public final class JsonObjectMarkdomHandler
		extends AbstractJsonObjectMarkdomHandler<JsonObjectBuilder, JsonArrayBuilder, JsonObject> {

	public JsonObjectMarkdomHandler() {
		this(false);
	}

	public JsonObjectMarkdomHandler(boolean includeSchema) {
		super(includeSchema);
	}

	@Override
	protected JsonObjectBuilder createObject() {
		return Json.createObjectBuilder();
	}

	@Override
	protected JsonArrayBuilder createArray() {
		return Json.createArrayBuilder();
	}

	@Override
	protected void putBoolean(JsonObjectBuilder object, String key, Boolean value) {
		object.add(key, value);
	}

	@Override
	protected void putInteger(JsonObjectBuilder object, String key, Integer value) {
		object.add(key, value);
	}

	@Override
	protected void putString(JsonObjectBuilder object, String key, String value) {
		object.add(key, value);
	}

	@Override
	protected void putArray(JsonObjectBuilder object, String key, JsonArrayBuilder value) {
		object.add(key, value);
	}

	@Override
	protected void addObject(JsonArrayBuilder array, JsonObjectBuilder object) {
		array.add(object);
	}

	@Override
	protected JsonObject toResult(JsonObjectBuilder object) {
		return object.build();
	}

}