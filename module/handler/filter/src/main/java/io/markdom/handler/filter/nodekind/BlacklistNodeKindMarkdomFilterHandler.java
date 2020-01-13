package io.markdom.handler.filter.nodekind;

import java.util.EnumSet;
import java.util.Set;

import io.markdom.common.MarkdomNodeKind;
import io.markdom.util.ObjectHelper;

public final class BlacklistNodeKindMarkdomFilterHandler implements NodeKindMarkdomFilterHandler {

	private final Set<MarkdomNodeKind> nodeKinds;

	public BlacklistNodeKindMarkdomFilterHandler(Set<MarkdomNodeKind> nodeKinds) {
		this.nodeKinds = EnumSet.copyOf(ObjectHelper.notNull("set of node kinds", nodeKinds));
	}

	@Override
	public boolean testNodeKind(MarkdomNodeKind kind) {
		return nodeKinds.contains(kind);
	}

}
