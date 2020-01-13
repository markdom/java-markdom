package io.markdom.handler.filter.contenttype;

import io.markdom.common.MarkdomContentType;

public final class IdleContentTypeMarkdomFilterHandler implements ContentTypeMarkdomFilterHandler {

	@Override
	public boolean testContentType(MarkdomContentType type) {
		return false;
	}

}
