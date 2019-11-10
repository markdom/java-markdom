package io.markdom.handler.json.jackson;

import java.util.Iterator;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.markdom.common.MarkdomException;
import io.markdom.handler.json.AbstractJsonObjectMarkdomDispatcher;
import lombok.SneakyThrows;

public final class JacksonJsonObjectMarkdomDispatcher extends AbstractJsonObjectMarkdomDispatcher<ObjectNode, ArrayNode> {

	private static final JsonNodeFactory JSON_NODE_FACTORY = new JsonNodeFactory(true);

	private final ObjectNode object;

	public JacksonJsonObjectMarkdomDispatcher(ObjectNode object) {
		if (null == object) {
			throw new IllegalArgumentException("The given object is null");
		}
		this.object = object;
	}

	@Override
	@SneakyThrows
	protected ObjectNode getRootObject() {
		return object;
	}

	@Override
	protected Iterator<ObjectNode> getObjects(ArrayNode jsonArray) {
		return new Iterator<ObjectNode>() {

			final Iterator<JsonNode> iterator = jsonArray.iterator();

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			@SneakyThrows
			public ObjectNode next() {
				JsonNode jsonNode = iterator.next();
				if (!jsonNode.isObject()) {
					throw new MarkdomException("Expected object node inside array");
				}
				return (ObjectNode) jsonNode;
			}

		};
	}

	@Override
	@SneakyThrows
	protected ArrayNode optArray(ObjectNode objectNode, String key) {
		JsonNode jsonNode = objectNode.get(key);
		if (null != jsonNode) {
			if (!jsonNode.isArray()) {
				throw new MarkdomException("Expected array node for key " + key);
			}
			return (ArrayNode) jsonNode;
		} else {
			return new ArrayNode(JSON_NODE_FACTORY);
		}
	}

	@Override
	@SneakyThrows
	protected Optional<String> optString(ObjectNode objectNode, String key) {
		JsonNode jsonNode = objectNode.get(key);
		if (null != jsonNode) {
			if (!jsonNode.isTextual()) {
				throw new MarkdomException("Expected text node for key " + key);
			}
			return Optional.of(jsonNode.asText());
		} else {
			return Optional.empty();
		}
	}

	@Override
	@SneakyThrows
	protected String reqString(ObjectNode objectNode, String key) {
		JsonNode jsonNode = objectNode.get(key);
		if (null == jsonNode || !jsonNode.isTextual()) {
			throw new MarkdomException("Expected text node for key " + key);
		}
		return jsonNode.asText();
	}

	@Override
	@SneakyThrows
	protected Boolean reqBoolean(ObjectNode objectNode, String key) {
		JsonNode jsonNode = objectNode.get(key);
		if (null == jsonNode || !jsonNode.isBoolean()) {
			throw new MarkdomException("Expected boolean node for key " + key);
		}
		return Boolean.valueOf(jsonNode.asBoolean());
	}

	@Override
	@SneakyThrows
	protected Integer reqInteger(ObjectNode objectNode, String key) {
		JsonNode jsonNode = objectNode.get(key);
		if (null == jsonNode || !jsonNode.isInt()) {
			throw new MarkdomException("Expected integer node for key " + key);
		}
		return Integer.valueOf(jsonNode.asInt());
	}

}
