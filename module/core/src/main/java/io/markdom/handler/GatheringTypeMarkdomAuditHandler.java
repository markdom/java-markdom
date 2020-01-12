package io.markdom.handler;

import java.util.EnumSet;
import java.util.Set;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;

public final class GatheringTypeMarkdomAuditHandler implements TypeMarkdomAuditHandler {

	private final Set<MarkdomBlockType> blockTypes = EnumSet.noneOf(MarkdomBlockType.class);

	private final Set<MarkdomContentType> contentTypes = EnumSet.noneOf(MarkdomContentType.class);

	@Override
	public void onBlockType(MarkdomBlockType type) {
		blockTypes.add(type);
	}

	@Override
	public void onContentType(MarkdomContentType type) {
		contentTypes.add(type);
	}

	public Set<MarkdomBlockType> getBlockTypes() {
		return EnumSet.copyOf(blockTypes);
	}

	public Set<MarkdomContentType> getContentTypes() {
		return EnumSet.copyOf(contentTypes);
	}

}
