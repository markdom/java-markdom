package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.markdom.common.MarkdomKeys;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomLineBreakContent;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Property;

public final class BasicMarkdomLineBreakContent extends AbstractMarkdomContent implements MarkdomLineBreakContent {

	// @formatter:off
	private static final List<Property<MarkdomLineBreakContent, ?>> PROPERTIES = new ArrayList<>(Arrays.asList(
		new Property<>(MarkdomKeys.HARD, MarkdomLineBreakContent::getHard)
	));
	// @formatter:on	

	private Boolean hard;

	BasicMarkdomLineBreakContent(MarkdomFactory factory, Boolean hard) {
		super(factory);
		setHard(hard);
	}

	@Override
	public Boolean getHard() {
		return hard;
	}

	@Override
	public BasicMarkdomLineBreakContent setHard(Boolean hard) {
		if (null == hard) {
			throw new IllegalArgumentException("The given hard is null");
		}
		this.hard = hard;
		return this;
	}

	@Override
	public void doHandle(MarkdomHandler<?> handler) {
		handler.onLineBreakContent(hard);
	}

	@Override
	public int hashCode() {
		return ObjectHelper.hashCode(this, PROPERTIES);
	}

	@Override
	public boolean equals(Object object) {
		return ObjectHelper.equals(this, MarkdomLineBreakContent.class, PROPERTIES, object);
	}

	@Override
	public String toString() {
		return ObjectHelper.toString(this, PROPERTIES);
	}

}
