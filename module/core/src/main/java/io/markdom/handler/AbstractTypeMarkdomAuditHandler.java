package io.markdom.handler;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;

public abstract class AbstractTypeMarkdomAuditHandler implements TypeMarkdomAuditHandler {

	@Override
	public void onBlockType(MarkdomBlockType type) {
	}

	@Override
	public void onContentType(MarkdomContentType type) {
	}

}
