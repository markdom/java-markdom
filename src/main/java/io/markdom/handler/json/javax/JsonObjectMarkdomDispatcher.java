package io.markdom.handler.json.javax;

import java.util.Iterator;
import java.util.Optional;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;

import io.markdom.handler.json.AbstractJsonObjectMarkdomDispatcher;

public final class JsonObjectMarkdomDispatcher extends AbstractJsonObjectMarkdomDispatcher<JsonObject, JsonArray> {

	private final JsonObject jsonObject;

	public JsonObjectMarkdomDispatcher(JsonObject jsonObject) {
		if (null == jsonObject) {
			throw new IllegalArgumentException("The given Json object is null");
		}
		this.jsonObject = jsonObject;
	}

	@Override
	protected JsonObject getRootObject() {
		return jsonObject;
	}

	@Override
	protected Iterator<JsonObject> getObjects(JsonArray jsonArray) {
		return new Iterator<JsonObject>() {

			final int length = jsonArray.size();

			int cursor = 0;

			@Override
			public boolean hasNext() {
				return cursor < length;
			}

			@Override
			public JsonObject next() {
				Object jsonNode = jsonArray.get(cursor++);
				if (!(jsonNode instanceof JsonObject)) {
					throw new JsonException("Expected object node inside array");
				}
				return (JsonObject) jsonNode;
			}

		};
	}

	@Override
	protected JsonArray optArray(JsonObject jsonObject, String key) {
		JsonArray value = jsonObject.getJsonArray(key);
		if (null == value) {
			return Json.createArrayBuilder().build();
		} else {
			return value;
		}
	}

	@Override
	protected Optional<String> optString(JsonObject jsonObject, String key) {
		return Optional.ofNullable(jsonObject.getString(key, null));
	}

	@Override
	protected String reqString(JsonObject jsonObject, String key) {
		return jsonObject.getString(key);
	}

	@Override
	protected Boolean reqBoolean(JsonObject jsonObject, String key) {
		return Boolean.valueOf(jsonObject.getBoolean(key));
	}

	@Override
	protected Integer reqInteger(JsonObject jsonObject, String key) {
		return Integer.valueOf(jsonObject.getInt(key));
	}

}
