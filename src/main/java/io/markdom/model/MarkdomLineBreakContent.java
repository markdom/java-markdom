package io.markdom.model;

import io.markdom.common.MarkdomContentType;

public interface MarkdomLineBreakContent extends MarkdomContent {

	@Override
	public default MarkdomContentType getContentType() {
		return MarkdomContentType.LINE_BREAK;
	}

	public Boolean getHard();

	public MarkdomLineBreakContent setHard(Boolean hard);

}
