package io.markdom.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import io.markdom.common.MarkdomBlockParentType;

public interface MarkdomBlockParent extends MarkdomNode, Iterable<MarkdomBlock> {

	public MarkdomBlockParentType getBlockParentType();

	public List<MarkdomBlock> getBlocks();

	public MarkdomBlockParent addBlock(MarkdomBlock block);

	public MarkdomBlockParent addBlocks(MarkdomBlock... blocks);

	public MarkdomBlockParent addBlocks(Iterable<MarkdomBlock> blocks);

	@Override
	public default List<MarkdomBlock> getChildren() {
		return Collections.unmodifiableList(getBlocks());
	}

	@Override
	public default Iterator<MarkdomBlock> iterator() {
		return getChildren().iterator();
	}

}
