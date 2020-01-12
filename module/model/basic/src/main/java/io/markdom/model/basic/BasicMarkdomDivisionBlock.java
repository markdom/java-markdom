package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomDivisionBlock;
import io.markdom.model.MarkdomFactory;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Property;

public final class BasicMarkdomDivisionBlock extends AbstractMarkdomBlock implements MarkdomDivisionBlock {

	// @formatter:off
	private static final List<Property<MarkdomDivisionBlock>> PROPERTIES = new ArrayList<>(Arrays.asList(
	));
	// @formatter:on	

	BasicMarkdomDivisionBlock(MarkdomFactory factory) {
		super(factory);
	}

	@Override
	public void doHandle(MarkdomHandler<?> handler) {
		handler.onDivisionBlock();
	}

	@Override
	public int hashCode() {
		return ObjectHelper.hashCode(this, PROPERTIES);
	}

	@Override
	public boolean equals(Object object) {
		return ObjectHelper.equals(this, MarkdomDivisionBlock.class, PROPERTIES, object);
	}

	@Override
	public String toString() {
		return ObjectHelper.toString(this, PROPERTIES);
	}

}
