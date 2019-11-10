package io.markdom.handler.json.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;

import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;

public class JacksonMarkdomDocumentModule extends SimpleModule {

	private static final long serialVersionUID = -1336129969582026768L;

	public JacksonMarkdomDocumentModule(MarkdomFactory factory) {
		this(factory, false);
	}

	public JacksonMarkdomDocumentModule(MarkdomFactory factory, boolean generateSchema) {
		if (null == factory) {
			throw new IllegalArgumentException("The given Markdom factory is null");
		}
		addSerializer(MarkdomDocument.class, new JacksonMarkdomDocumentSerializer(generateSchema));
		addDeserializer(MarkdomDocument.class, new JacksonMarkdomDocumentDeserializer(factory));
	}

}
