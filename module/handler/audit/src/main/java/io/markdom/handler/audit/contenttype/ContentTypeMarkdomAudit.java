package io.markdom.handler.audit.contenttype;

import java.util.Optional;

import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.handler.AbstractMarkdomAudit;
import io.markdom.util.ObjectHelper;

public final class ContentTypeMarkdomAudit extends AbstractMarkdomAudit {

	private final ContentTypeMarkdomAuditHandler handler;

	public ContentTypeMarkdomAudit(ContentTypeMarkdomAuditHandler handler) {
		this.handler = ObjectHelper.notNull("handler", handler);
	}

	@Override
	public void onCodeContent(String code) {
		handler.onContentType(MarkdomContentType.CODE);
	}

	@Override
	public void onEmphasisContent(MarkdomEmphasisLevel level) {
		handler.onContentType(MarkdomContentType.EMPHASIS);
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		handler.onContentType(MarkdomContentType.IMAGE);
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		handler.onContentType(MarkdomContentType.LINE_BREAK);
	}

	@Override
	public void onLinkContent(String uri, Optional<String> title) {
		handler.onContentType(MarkdomContentType.LINK);
	}

	@Override
	public void onTextContent(String text) {
		handler.onContentType(MarkdomContentType.TEXT);
	}

}
