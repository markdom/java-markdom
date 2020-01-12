package io.markdom.handler.json.org;

import java.util.Iterator;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import io.markdom.common.MarkdomException;
import io.markdom.handler.json.AbstractJsonObjectMarkdomDispatcher;
import io.markdom.util.ObjectHelper;

public final class JsonObjectMarkdomDispatcher extends AbstractJsonObjectMarkdomDispatcher<JSONObject, JSONArray> {

	private final JSONObject object;

	public JsonObjectMarkdomDispatcher(JSONObject object) {
		this.object = ObjectHelper.notNull("object", object);
	}

	@Override
	protected JSONObject getRootObject() {
		return object;
	}

	@Override
	protected Iterator<JSONObject> getObjects(JSONArray jsonArray) {
		return new Iterator<JSONObject>() {

			final int length = jsonArray.length();

			int cursor = 0;

			@Override
			public boolean hasNext() {
				return cursor < length;
			}

			@Override
			public JSONObject next() {
				Object jsonNode = jsonArray.get(cursor++);
				if (!(jsonNode instanceof JSONObject)) {
					throw new MarkdomException("Expected object node inside array");
				}
				return (JSONObject) jsonNode;
			}

		};
	}

	@Override
	protected JSONArray optArray(JSONObject jsonObject, String key) {
		JSONArray value = jsonObject.optJSONArray(key);
		if (null == value) {
			return new JSONArray();
		} else {
			return value;
		}
	}

	@Override
	protected Optional<String> optString(JSONObject jsonObject, String key) {
		return Optional.ofNullable(jsonObject.optString(key, null));
	}

	@Override
	protected String reqString(JSONObject jsonObject, String key) {
		return jsonObject.getString(key);
	}

	@Override
	protected Boolean reqBoolean(JSONObject jsonObject, String key) {
		return Boolean.valueOf(jsonObject.getBoolean(key));
	}

	@Override
	protected Integer reqInteger(JSONObject jsonObject, String key) {
		return Integer.valueOf(jsonObject.getInt(key));
	}

}
