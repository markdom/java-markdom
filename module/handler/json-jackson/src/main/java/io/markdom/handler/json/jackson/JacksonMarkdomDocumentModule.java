package io.markdom.handler.json.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;

import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.util.ObjectHelper;

public final class JacksonMarkdomDocumentModule extends SimpleModule {

	private static final long serialVersionUID = -1336129969582026768L;

	public JacksonMarkdomDocumentModule(MarkdomFactory factory) {
		this(factory, false);
	}

	public JacksonMarkdomDocumentModule(MarkdomFactory factory, boolean generateSchema) {
		factory = ObjectHelper.notNull("factory", factory);
		addSerializer(MarkdomDocument.class, new JacksonMarkdomDocumentSerializer(generateSchema));
		addDeserializer(MarkdomDocument.class, new JacksonMarkdomDocumentDeserializer(factory));
	}

}
