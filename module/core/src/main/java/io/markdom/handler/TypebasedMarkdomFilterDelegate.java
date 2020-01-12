package io.markdom.handler;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;

public interface TypebasedMarkdomFilterDelegate {

	public boolean testBlockType(MarkdomBlockType type);

	public boolean testContentType(MarkdomContentType type);

}
