package io.markdom.model;

import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomNodeType;

public interface MarkdomContent extends MarkdomNode {

	@Override
	public default MarkdomNodeType getNodeType() {
		return MarkdomNodeType.CONTENT;
	}

	public MarkdomContentType getContentType();

}
