package io.markdom.handler;

import io.markdom.common.MarkdomNodeKind;

public interface NodeKindMarkdomFilterHandler {

	public boolean testNodeKind(MarkdomNodeKind kind);

}
