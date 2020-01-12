package io.markdom.handler.json.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import io.markdom.handler.MarkdomDocumentMarkdomHandler;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.basic.BasicMarkdomFactory;
import io.markdom.util.ObjectHelper;

public final class GsonMarkdomDocumentDeserializer implements JsonDeserializer<MarkdomDocument> {

	private final MarkdomFactory factory;

	public GsonMarkdomDocumentDeserializer() {
		this(new BasicMarkdomFactory());
	}

	public GsonMarkdomDocumentDeserializer(MarkdomFactory factory) {
		this.factory = ObjectHelper.notNull("factory", factory);	}

	@Override
	public MarkdomDocument deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return new GsonJsonObjectMarkdomDispatcher(json.getAsJsonObject()).handle(new MarkdomDocumentMarkdomHandler(factory));
	}

}
