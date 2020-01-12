package io.markdom.handler;

import java.util.EnumSet;
import java.util.Set;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.util.ObjectHelper;

public final class BlacklistTypeMarkdomFilterDelegate implements TypeMarkdomFilterDelegate {

	private final Set<MarkdomBlockType> blockTypes;

	private final Set<MarkdomContentType> contentTypes;

	public BlacklistTypeMarkdomFilterDelegate(Set<MarkdomBlockType> blockTypes, Set<MarkdomContentType> contentTypes) {
		this.blockTypes = EnumSet.copyOf(ObjectHelper.notNull("set of block types", blockTypes));
		this.contentTypes = EnumSet.copyOf(ObjectHelper.notNull("set of content types", contentTypes));
	}

	@Override
	public boolean testBlockType(MarkdomBlockType type) {
		return blockTypes.contains(type);
	}

	@Override
	public boolean testContentType(MarkdomContentType type) {
		return contentTypes.contains(type);
	}

}
