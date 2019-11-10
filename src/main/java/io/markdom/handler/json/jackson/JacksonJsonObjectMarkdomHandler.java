package io.markdom.handler.json.jackson;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.markdom.handler.json.AbstractJsonObjectMarkdomHandler;
import io.markdom.util.ObjectHelper;

public final class JacksonJsonObjectMarkdomHandler
	extends AbstractJsonObjectMarkdomHandler<ObjectNode, ArrayNode, JacksonJsonObjectResult> {

	private final JsonNodeFactory factory;

	public JacksonJsonObjectMarkdomHandler() {
		this(new JsonNodeFactory(false), false);
	}

	public JacksonJsonObjectMarkdomHandler(boolean includeSchema) {
		this(new JsonNodeFactory(false), includeSchema);
	}

	public JacksonJsonObjectMarkdomHandler(JsonNodeFactory factory) {
		this(factory, false);
	}

	public JacksonJsonObjectMarkdomHandler(JsonNodeFactory factory, boolean includeSchema) {
		super(includeSchema);
		this.factory = ObjectHelper.notNull("factory", factory);
	}

	@Override
	protected ObjectNode createObject() {
		return new ObjectNode(factory);
	}

	@Override
	protected ArrayNode createArray() {
		return new ArrayNode(factory);
	}

	@Override
	protected void putBoolean(ObjectNode object, String key, Boolean value) {
		object.set(key, factory.booleanNode(value));
	}

	@Override
	protected void putInteger(ObjectNode object, String key, Integer value) {
		object.set(key, factory.numberNode(value));
	}

	@Override
	protected void putString(ObjectNode object, String key, String value) {
		object.set(key, factory.textNode(value));
	}

	@Override
	protected void putArray(ObjectNode object, String key, ArrayNode value) {
		object.set(key, value);
	}

	@Override
	protected void addObject(ArrayNode array, ObjectNode object) {
		array.add(object);
	}

	@Override
	protected JacksonJsonObjectResult toResult(ObjectNode object) {
		return new JacksonJsonObjectResult(object);
	}

}