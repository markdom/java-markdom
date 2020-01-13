package io.markdom.handler.audit.blocktype;

import java.util.function.Consumer;

import io.markdom.common.MarkdomBlockType;
import io.markdom.util.ObjectHelper;

public final class ConsumingBlockTypeMarkdomAuditHandler implements BlockTypeMarkdomAuditHandler {

	private final Consumer<MarkdomBlockType> blockTypesConsumer;

	public ConsumingBlockTypeMarkdomAuditHandler(Consumer<MarkdomBlockType> blockTypesConsumer) {
		this.blockTypesConsumer = ObjectHelper.notNull("block type consumer", blockTypesConsumer);
	}

	@Override
	public void onBlockType(MarkdomBlockType type) {
		blockTypesConsumer.accept(type);
	}

}
