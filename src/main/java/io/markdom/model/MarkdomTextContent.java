package io.markdom.model;

import io.markdom.common.MarkdomContentType;

public interface MarkdomTextContent extends MarkdomContent {

	@Override
	public default MarkdomContentType getContentType() {
		return MarkdomContentType.TEXT;
	}

	public String getText();

	public MarkdomTextContent setText(String text);

}
