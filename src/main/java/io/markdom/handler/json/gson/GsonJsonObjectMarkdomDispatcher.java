package io.markdom.handler.json.gson;

import java.util.Iterator;
import java.util.Optional;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.markdom.common.MarkdomException;
import io.markdom.handler.json.AbstractJsonObjectMarkdomDispatcher;
import io.markdom.util.ObjectHelper;

public final class GsonJsonObjectMarkdomDispatcher extends AbstractJsonObjectMarkdomDispatcher<JsonObject, JsonArray> {

	private final JsonObject object;

	public GsonJsonObjectMarkdomDispatcher(JsonObject object) {
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
		JsonElement element = jsonObject.get(key);
		if (null == element) {
			return new JsonArray();
		} else {
			return element.getAsJsonArray();
		}
	}

	@Override
	protected Optional<String> optString(JsonObject jsonObject, String key) {
		return Optional.ofNullable(jsonObject.get(key)).map(JsonElement::getAsString);
	}

	@Override
	protected String reqString(JsonObject jsonObject, String key) {
		JsonElement element = jsonObject.get(key);
		if (null == element) {
			throw new MarkdomException("Expected text node for key " + key);
		} else {
			return element.getAsString();
		}
	}

	@Override
	protected Boolean reqBoolean(JsonObject jsonObject, String key) {
		JsonElement element = jsonObject.get(key);
		if (null == element) {
			throw new MarkdomException("Expected boolean node for key " + key);
		} else {
			return element.getAsBoolean();
		}
	}

	@Override
	protected Integer reqInteger(JsonObject jsonObject, String key) {
		JsonElement element = jsonObject.get(key);
		if (null == element) {
			throw new MarkdomException("Expected integer node for key " + key);
		} else {
			return element.getAsInt();
		}
	}

}
