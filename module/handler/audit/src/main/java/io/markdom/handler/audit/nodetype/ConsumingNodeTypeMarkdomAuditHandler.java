package io.markdom.handler.audit.nodetype;

import java.util.function.Consumer;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.util.ObjectHelper;

public final class ConsumingNodeTypeMarkdomAuditHandler implements NodeTypeMarkdomAuditHandler {

	private final Consumer<MarkdomBlockType> blockTypesConsumer;

	private final Consumer<MarkdomContentType> contentTypeConsumer;

	public ConsumingNodeTypeMarkdomAuditHandler(Consumer<MarkdomBlockType> blockTypesConsumer,
			Consumer<MarkdomContentType> contentTypeConsumer) {
		this.blockTypesConsumer = ObjectHelper.notNull("block type consumer", blockTypesConsumer);
		this.contentTypeConsumer = ObjectHelper.notNull("content type consumer", contentTypeConsumer);
	}

	@Override
	public void onBlockType(MarkdomBlockType type) {
		blockTypesConsumer.accept(type);
	}

	@Override
	public void onContentType(MarkdomContentType type) {
		contentTypeConsumer.accept(type);
	}

}
