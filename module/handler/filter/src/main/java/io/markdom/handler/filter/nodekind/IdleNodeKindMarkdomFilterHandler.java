package io.markdom.handler.filter.nodekind;

import io.markdom.common.MarkdomNodeKind;

public final class IdleNodeKindMarkdomFilterHandler implements NodeKindMarkdomFilterHandler {

	@Override
	public boolean testNodeKind(MarkdomNodeKind kind) {
		return false;
	}

}
