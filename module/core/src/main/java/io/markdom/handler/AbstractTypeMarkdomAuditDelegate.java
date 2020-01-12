package io.markdom.handler;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;

public abstract class AbstractTypeMarkdomAuditDelegate implements TypeMarkdomAuditDelegate {

	@Override
	public void onBlockType(MarkdomBlockType type) {
	}

	@Override
	public void onContentType(MarkdomContentType type) {
	}

}
