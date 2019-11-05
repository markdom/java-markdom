package io.markdom.handler.json.simple;

import java.util.Iterator;
import java.util.Optional;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.JsonObject;

import io.markdom.common.MarkdomException;
import io.markdom.handler.json.AbstractJsonMarkdomDispatcher;

public final class JsonObjectMarkdomDispatcher extends AbstractJsonMarkdomDispatcher<JsonObject, JsonArray> {

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
					throw new MarkdomException("Expected object node inside array");
				}
				return (JsonObject) jsonNode;
			}

		};
	}

	@Override
	protected JsonArray optArray(JsonObject jsonObject, String key) {
		JsonArray value = jsonObject.getCollection(getJsonKey(key));
		if (null == value) {
			return new JsonArray();
		} else {
			return value;
		}
	}

	@Override
	protected Optional<String> optString(JsonObject jsonObject, String key) {
		return Optional.ofNullable(jsonObject.getString(getJsonKey(key)));
	}

	@Override
	protected String reqString(JsonObject jsonObject, String key) {
		String value = jsonObject.getString(getJsonKey(key));
		if (null == value) {
			throw new MarkdomException("Expected text node for key " + key);
		} else {
			return value;
		}
	}

	@Override
	protected Boolean reqBoolean(JsonObject jsonObject, String key) {
		Boolean value = jsonObject.getBoolean(getJsonKey(key));
		if (null == value) {
			throw new MarkdomException("Expected boolean node for key " + key);
		} else {
			return value;
		}
	}

	@Override
	protected Integer reqInteger(JsonObject jsonObject, String key) {
		Integer value = jsonObject.getInteger(getJsonKey(key));
		if (null == value) {
			throw new MarkdomException("Expected integer node for key " + key);
		} else {
			return value;
		}
	}

	private JsonKey getJsonKey(String key) {
		return new JsonKey() {

			@Override
			public String getKey() {
				return key;
			}

			@Override
			public Object getValue() {
				return null;
			}

		};
	}

}
