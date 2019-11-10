package io.markdom.handler.json.org;

import org.json.JSONArray;
import org.json.JSONObject;

import io.markdom.handler.json.AbstractJsonObjectMarkdomHandler;

public final class JsonObjectMarkdomHandler extends AbstractJsonObjectMarkdomHandler<JSONObject, JSONArray, JsonObjectResult> {

	public JsonObjectMarkdomHandler() {
		this(false);
	}

	public JsonObjectMarkdomHandler(boolean includeSchema) {
		super(includeSchema);
	}

	@Override
	protected JSONObject createObject() {
		return new JSONObject();
	}

	@Override
	protected JSONArray createArray() {
		return new JSONArray();
	}

	@Override
	protected void putBoolean(JSONObject object, String key, Boolean value) {
		object.put(key, value);
	}

	@Override
	protected void putInteger(JSONObject object, String key, Integer value) {
		object.put(key, value);
	}

	@Override
	protected void putString(JSONObject object, String key, String value) {
		object.put(key, value);
	}

	@Override
	protected void putArray(JSONObject object, String key, JSONArray value) {
		object.put(key, value);
	}

	@Override
	protected void addObject(JSONArray array, JSONObject object) {
		array.put(object);
	}

	@Override
	protected JsonObjectResult toResult(JSONObject object) {
		return new JsonObjectResult(object);
	}

}