package io.markdom.handler.audit.contenttype;

import java.util.EnumSet;
import java.util.Set;

import io.markdom.common.MarkdomContentType;

public final class GatheringContentTypeMarkdomAuditHandler implements ContentTypeMarkdomAuditHandler {

	private final Set<MarkdomContentType> contentTypes = EnumSet.noneOf(MarkdomContentType.class);

	@Override
	public void onContentType(MarkdomContentType type) {
		contentTypes.add(type);
	}

	public Set<MarkdomContentType> getContentTypes() {
		return EnumSet.copyOf(contentTypes);
	}

}
