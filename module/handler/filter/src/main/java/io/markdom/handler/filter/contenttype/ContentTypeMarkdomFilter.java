package io.markdom.handler.filter.contenttype;

import java.util.Optional;

import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.handler.AbstractMarkdomFilter;
import io.markdom.util.ObjectHelper;

public final class ContentTypeMarkdomFilter extends AbstractMarkdomFilter {

	private final ContentTypeMarkdomFilterHandler handler;

	public ContentTypeMarkdomFilter(ContentTypeMarkdomFilterHandler handler) {
		this.handler = ObjectHelper.notNull("handler", handler);
	}

	@Override
	public boolean testCodeContent(String code) {
		return handler.testContentType(MarkdomContentType.CODE);
	}

	@Override
	public boolean testEmphasisContent(MarkdomEmphasisLevel level) {
		return handler.testContentType(MarkdomContentType.EMPHASIS);
	}

	@Override
	public boolean testImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		return handler.testContentType(MarkdomContentType.IMAGE);
	}

	@Override
	public boolean testLineBreakContent(Boolean hard) {
		return handler.testContentType(MarkdomContentType.LINE_BREAK);
	}

	@Override
	public boolean testLinkContent(String uri, Optional<String> title) {
		return handler.testContentType(MarkdomContentType.LINK);
	}

	@Override
	public boolean testTextContent(String text) {
		return handler.testContentType(MarkdomContentType.TEXT);
	}

}
