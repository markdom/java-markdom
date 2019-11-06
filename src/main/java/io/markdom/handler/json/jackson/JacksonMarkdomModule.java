package io.markdom.handler.json.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;

import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;

public class JacksonMarkdomModule extends SimpleModule {

	private static final long serialVersionUID = -1336129969582026768L;

	public JacksonMarkdomModule(MarkdomFactory factory) {
		this(factory, false);
	}

	public JacksonMarkdomModule(MarkdomFactory factory, boolean generateSchema) {
		if (null == factory) {
			throw new IllegalArgumentException("The given Markdom factory is null");
		}
		addSerializer(MarkdomDocument.class, new JacksonMarkdomSerializer(generateSchema));
		addDeserializer(MarkdomDocument.class, new JacksonMarkdomDeserializer(factory));
	}

}
