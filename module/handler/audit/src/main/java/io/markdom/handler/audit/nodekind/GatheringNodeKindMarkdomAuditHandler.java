package io.markdom.handler.audit.nodekind;

import java.util.EnumSet;
import java.util.Set;

import io.markdom.common.MarkdomNodeKind;

public final class GatheringNodeKindMarkdomAuditHandler implements NodeKindMarkdomAuditHandler {

	private final Set<MarkdomNodeKind> nodeKinds = EnumSet.noneOf(MarkdomNodeKind.class);

	@Override
	public void onNodeKind(MarkdomNodeKind kind) {
		nodeKinds.add(kind);
	}

	public Set<MarkdomNodeKind> getNodeKinds() {
		return EnumSet.copyOf(nodeKinds);
	}

}
