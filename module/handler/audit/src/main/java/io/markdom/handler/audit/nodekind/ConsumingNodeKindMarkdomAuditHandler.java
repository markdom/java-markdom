package io.markdom.handler.audit.nodekind;

import java.util.function.Consumer;

import io.markdom.common.MarkdomNodeKind;
import io.markdom.util.ObjectHelper;

public final class ConsumingNodeKindMarkdomAuditHandler implements NodeKindMarkdomAuditHandler {

	private final Consumer<MarkdomNodeKind> nodeKindConsumer;

	public ConsumingNodeKindMarkdomAuditHandler(Consumer<MarkdomNodeKind> nodeKindConsumer) {
		this.nodeKindConsumer = ObjectHelper.notNull("node kind consumer", nodeKindConsumer);
	}

	@Override
	public void onNodeKind(MarkdomNodeKind kind) {
		nodeKindConsumer.accept(kind);
	}

}
