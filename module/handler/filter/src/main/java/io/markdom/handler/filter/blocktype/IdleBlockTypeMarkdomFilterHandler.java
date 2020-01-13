package io.markdom.handler.filter.blocktype;

import io.markdom.common.MarkdomBlockType;

public final class IdleBlockTypeMarkdomFilterHandler implements BlockTypeMarkdomFilterHandler {

	@Override
	public boolean testBlockType(MarkdomBlockType type) {
		return false;
	}

}
