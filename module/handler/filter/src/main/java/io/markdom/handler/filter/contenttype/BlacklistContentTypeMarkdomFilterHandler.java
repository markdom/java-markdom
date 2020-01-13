package io.markdom.handler.filter.contenttype;

import java.util.EnumSet;
import java.util.Set;

import io.markdom.common.MarkdomContentType;
import io.markdom.util.ObjectHelper;

public final class BlacklistContentTypeMarkdomFilterHandler implements ContentTypeMarkdomFilterHandler {

	private final Set<MarkdomContentType> contentTypes;

	public BlacklistContentTypeMarkdomFilterHandler(Set<MarkdomContentType> contentTypes) {
		this.contentTypes = EnumSet.copyOf(ObjectHelper.notNull("set of content types", contentTypes));
	}

	@Override
	public boolean testContentType(MarkdomContentType type) {
		return contentTypes.contains(type);
	}

}
