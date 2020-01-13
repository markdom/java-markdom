package io.markdom.handler.filter.nodetype;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;

public interface NodeTypeMarkdomFilterHandler {

	public boolean testBlockType(MarkdomBlockType type);

	public boolean testContentType(MarkdomContentType type);

}
