package io.markdom.handler.json.jakarta;

import java.util.Iterator;
import java.util.Optional;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import io.markdom.common.MarkdomException;
import io.markdom.handler.json.AbstractJsonObjectMarkdomDispatcher;
import io.markdom.util.ObjectHelper;

public final class JakartaJsonObjectMarkdomDispatcher extends AbstractJsonObjectMarkdomDispatcher<JsonObject, JsonArray> {

	private final JsonObject object;

	public JakartaJsonObjectMarkdomDispatcher(JsonObject object) {
		this.object = ObjectHelper.notNull("object", object);
	}

	@Override
	protected JsonObject getRootObject() {
		return object;
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
					throw new MarkdomException("Expected object node inside array");
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
