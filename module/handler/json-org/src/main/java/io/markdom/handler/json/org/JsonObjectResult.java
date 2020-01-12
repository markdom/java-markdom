package io.markdom.handler.json.org;

import org.json.JSONObject;

import io.markdom.util.ObjectHelper;

public class JsonObjectResult implements io.markdom.handler.json.JsonObjectResult<JSONObject> {

	private final JSONObject object;

	public JsonObjectResult(JSONObject object) {
		this.object = ObjectHelper.notNull("object", object);
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
