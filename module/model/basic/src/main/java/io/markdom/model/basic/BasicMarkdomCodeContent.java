package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.markdom.common.MarkdomKeys;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomCodeContent;
import io.markdom.model.MarkdomFactory;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Property;

public final class BasicMarkdomCodeContent extends AbstractMarkdomContent implements MarkdomCodeContent {

	// @formatter:off
	private static final List<Property<MarkdomCodeContent>> PROPERTIES = new ArrayList<>(Arrays.asList(
		new Property<>(MarkdomKeys.CODE, MarkdomCodeContent::getCode)
	));
	// @formatter:on		

	private String code;

	BasicMarkdomCodeContent(MarkdomFactory factory, String code) {
		super(factory);
		setCode(code);
	}

	@Override
	public String getCode() {
		return code;
	}

	public BasicMarkdomCodeContent setCode(String code) {
		this.code = ObjectHelper.notNull("code", code);;
		return this;
	}

	@Override
	public void doHandle(MarkdomHandler<?> handler) {
		handler.onCodeContent(code);
	}

	@Override
	public int hashCode() {
		return ObjectHelper.hashCode(this, PROPERTIES);
	}

	@Override
	public boolean equals(Object object) {
		return ObjectHelper.equals(this, MarkdomCodeContent.class, PROPERTIES, object);
	}

	@Override
	public String toString() {
		return ObjectHelper.toString(this, PROPERTIES);
	}

}
