package io.markdom.handler.json.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.markdom.handler.json.JsonObjectResult;
import io.markdom.util.ObjectHelper;

public class JacksonJsonObjectResult implements JsonObjectResult<JsonNode> {

	private final ObjectNode object;

	public JacksonJsonObjectResult(ObjectNode object) {
		this.object = ObjectHelper.notNull("object", object);
	}

	@Override
	public ObjectNode asObject() {
		return object;
	}

	@Override
	public String asObjectText(boolean pretty) {
		return pretty ? object.toPrettyString() : object.toString();
	}

}
