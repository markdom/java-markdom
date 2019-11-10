package io.markdom.handler.json.org;

import org.json.JSONObject;

public class JsonObjectResult implements io.markdom.handler.json.JsonObjectResult<JSONObject> {

	private final JSONObject object;

	public JsonObjectResult(JSONObject object) {
		if (null == object) {
			throw new IllegalArgumentException("The given object is null");
		}
		this.object = object;
	}

	@Override
	public JSONObject asObject() {
		return object;
	}

	@Override
	public String asObjectText(boolean pretty) {
		return pretty ? object.toString(2) : object.toString();
	}

}
