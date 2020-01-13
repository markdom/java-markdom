package io.markdom.handler.filter.blocktype;

import java.util.EnumSet;
import java.util.Set;

import io.markdom.common.MarkdomBlockType;
import io.markdom.util.ObjectHelper;

public final class WhitelistBlockTypeMarkdomFilterHandler implements BlockTypeMarkdomFilterHandler {

	private final Set<MarkdomBlockType> blockTypes;

	public WhitelistBlockTypeMarkdomFilterHandler(Set<MarkdomBlockType> blockTypes) {
		this.blockTypes = EnumSet.copyOf(ObjectHelper.notNull("set of block types", blockTypes));
	}

	@Override
	public boolean testBlockType(MarkdomBlockType type) {
		return !blockTypes.contains(type);
	}

}
