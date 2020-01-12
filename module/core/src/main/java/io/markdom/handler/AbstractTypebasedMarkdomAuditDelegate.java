package io.markdom.handler;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;

public abstract class AbstractTypebasedMarkdomAuditDelegate implements TypebasedMarkdomAuditDelegate {

	@Override
	public void onBlockType(MarkdomBlockType type) {
	}

	@Override
	public void onContentType(MarkdomContentType type) {
	}

}
