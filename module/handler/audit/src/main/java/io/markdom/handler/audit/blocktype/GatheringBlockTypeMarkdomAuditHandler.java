package io.markdom.handler.audit.blocktype;

import java.util.EnumSet;
import java.util.Set;

import io.markdom.common.MarkdomBlockType;

public final class GatheringBlockTypeMarkdomAuditHandler implements BlockTypeMarkdomAuditHandler {

	private final Set<MarkdomBlockType> blockTypes = EnumSet.noneOf(MarkdomBlockType.class);

	@Override
	public void onBlockType(MarkdomBlockType type) {
		blockTypes.add(type);
	}

	public Set<MarkdomBlockType> getBlockTypes() {
		return EnumSet.copyOf(blockTypes);
	}

}
