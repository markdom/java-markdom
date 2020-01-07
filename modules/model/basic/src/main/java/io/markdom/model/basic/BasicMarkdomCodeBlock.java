package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import io.markdom.common.MarkdomKeys;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomCodeBlock;
import io.markdom.model.MarkdomFactory;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Property;

public final class BasicMarkdomCodeBlock extends AbstractMarkdomBlock implements MarkdomCodeBlock {

	// @formatter:off
	private static final List<Property<MarkdomCodeBlock>> PROPERTIES = new ArrayList<>(Arrays.asList(
		new Property<>(MarkdomKeys.CODE, MarkdomCodeBlock::getCode),
		new Property<>(MarkdomKeys.HINT, MarkdomCodeBlock::getHint)
	));
	// @formatter:on		

	private String code;

	private Optional<String> hint;

	BasicMarkdomCodeBlock(MarkdomFactory factory, String code, Optional<String> hint) {
		super(factory);
		setCode(code);
		setHint(hint);
	}

	@Override
	public String getCode() {
		return code;
	}

	public BasicMarkdomCodeBlock setCode(String code) {
		this.code = ObjectHelper.notNull("code", code);
		return this;
	}

	@Override
	public Optional<String> getHint() {
		return hint;
	}

	public BasicMarkdomCodeBlock setHint(Optional<String> hint) {
		this.hint = ObjectHelper.notNull("optional hint", hint);
		return this;
	}

	@Override
	public void doHandle(MarkdomHandler<?> handler) {
		handler.onCodeBlock(code, hint);
	}

	@Override
	public int hashCode() {
		return ObjectHelper.hashCode(this, PROPERTIES);
	}

	@Override
	public boolean equals(Object object) {
		return ObjectHelper.equals(this, MarkdomCodeBlock.class, PROPERTIES, object);
	}

	@Override
	public String toString() {
		return ObjectHelper.toString(this, PROPERTIES);
	}

}
