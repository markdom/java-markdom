package io.markdom.model;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;

public interface MarkdomCodeBlock extends MarkdomBlock {

	@Override
	public default MarkdomBlockType getBlockType() {
		return MarkdomBlockType.CODE;
	}

	public String getCode();

	public MarkdomCodeBlock setCode(String code);

	public Optional<String> getHint();

	public MarkdomCodeBlock setHint(Optional<String> hint);

}
