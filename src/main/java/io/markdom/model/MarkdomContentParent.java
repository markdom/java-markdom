package io.markdom.model;

import java.util.Collections;
import java.util.List;

import io.markdom.common.MarkdomContentParentType;

public interface MarkdomContentParent extends MarkdomNode {

	public MarkdomContentParentType getContentParentType();

	public List<? extends MarkdomContent> getContents();

	public MarkdomContentParent addContent(MarkdomContent content);

	public MarkdomContentParent addContents(MarkdomContent... contents);

	public MarkdomContentParent addContents(Iterable<MarkdomContent> contents);

	@Override
	public default List<MarkdomContent> getChildren() {
		return Collections.unmodifiableList(getContents());
	}

}
