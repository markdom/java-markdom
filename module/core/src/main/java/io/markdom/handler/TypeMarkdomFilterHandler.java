package io.markdom.handler;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;

public interface TypeMarkdomFilterHandler {

	public boolean testBlockType(MarkdomBlockType type);

	public boolean testContentType(MarkdomContentType type);

}
