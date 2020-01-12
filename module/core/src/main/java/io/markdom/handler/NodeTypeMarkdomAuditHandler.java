package io.markdom.handler;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;

public interface NodeTypeMarkdomAuditHandler {

	public void onBlockType(MarkdomBlockType type);

	public void onContentType(MarkdomContentType type);

}
