package io.markdom.handler.filter.nodekind;

import io.markdom.common.MarkdomNodeKind;

public interface NodeKindMarkdomFilterHandler {

	public boolean testNodeKind(MarkdomNodeKind kind);

}
