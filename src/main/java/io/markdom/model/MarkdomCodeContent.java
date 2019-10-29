package io.markdom.model;

import io.markdom.common.MarkdomContentType;

public interface MarkdomCodeContent extends MarkdomContent {

	@Override
	public default MarkdomContentType getContentType() {
		return MarkdomContentType.CODE;
	}

	public String getCode();

	public MarkdomCodeContent setCode(String code);

}
