package io.markdom.handler.audit.contenttype;

import java.util.function.Consumer;

import io.markdom.common.MarkdomContentType;
import io.markdom.util.ObjectHelper;

public final class ConsumingContentTypeMarkdomAuditHandler implements ContentTypeMarkdomAuditHandler {

	private final Consumer<MarkdomContentType> contentTypesConsumer;

	public ConsumingContentTypeMarkdomAuditHandler(Consumer<MarkdomContentType> contentTypesConsumer) {
		this.contentTypesConsumer = ObjectHelper.notNull("content type consumer", contentTypesConsumer);
	}

	@Override
	public void onContentType(MarkdomContentType type) {
		contentTypesConsumer.accept(type);
	}

}
