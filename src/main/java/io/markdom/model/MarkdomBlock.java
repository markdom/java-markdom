package io.markdom.model;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomNodeType;

public interface MarkdomBlock extends MarkdomNode {

	@Override
	public default MarkdomNodeType getNodeType() {
		return MarkdomNodeType.BLOCK;
	}

	public MarkdomBlockType getBlockType();

	@Override
	public Optional<MarkdomBlockParent> getParent();



}
