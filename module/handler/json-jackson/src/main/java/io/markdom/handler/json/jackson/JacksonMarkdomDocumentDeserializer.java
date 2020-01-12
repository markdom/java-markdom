package io.markdom.handler.json.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import io.markdom.handler.MarkdomDocumentMarkdomHandler;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import io.markdom.util.ObjectHelper;

public final class JacksonMarkdomDocumentDeserializer extends JsonDeserializer<MarkdomDocument> {

	private final MarkdomFactory factory;

	public JacksonMarkdomDocumentDeserializer() {
		this(new BasicMarkdomFactory());
	}

	public JacksonMarkdomDocumentDeserializer(MarkdomFactory factory) {
		this.factory = ObjectHelper.notNull("factory", factory);
	}

	@Override
	public MarkdomDocument deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		return new JacksonJsonObjectMarkdomDispatcher(parser.readValueAsTree()).handle(new MarkdomDocumentMarkdomHandler(factory));
	}

}
