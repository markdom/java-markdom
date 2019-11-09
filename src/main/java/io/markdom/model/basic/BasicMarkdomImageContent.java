package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import io.markdom.common.MarkdomKeys;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomImageContent;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Property;

public final class BasicMarkdomImageContent extends AbstractMarkdomContent implements MarkdomImageContent {

	// @formatter:off
	private static final List<Property<MarkdomImageContent, ?>> PROPERTIES = new ArrayList<>(Arrays.asList(
		new Property<>(MarkdomKeys.URI, MarkdomImageContent::getUri),
		new Property<>(MarkdomKeys.TITLE, MarkdomImageContent::getTitle),
		new Property<>(MarkdomKeys.ALTERNATIVE, MarkdomImageContent::getAlternative)
	));
	// @formatter:on	

	private String uri;

	private Optional<String> title;

	private Optional<String> alternative;

	BasicMarkdomImageContent(MarkdomFactory factory, String uri, Optional<String> title, Optional<String> alternative) {
		super(factory);
		setUri(uri);
		setTitle(title);
		setAlternative(alternative);
	}

	@Override
	public String getUri() {
		return uri;
	}

	public BasicMarkdomImageContent setUri(String uri) {
		if (null == uri) {
			throw new IllegalArgumentException("The given URI is null");
		}
		this.uri = uri;
		return this;
	}

	@Override
	public Optional<String> getTitle() {
		return title;
	}

	public BasicMarkdomImageContent setTitle(Optional<String> title) {
		if (null == uri) {
			throw new IllegalArgumentException("The given title	 is null");
		}
		this.title = title;
		return this;
	}

	@Override
	public Optional<String> getAlternative() {
		return alternative;
	}

	public BasicMarkdomImageContent setAlternative(Optional<String> alternative) {
		if (null == uri) {
			throw new IllegalArgumentException("The given alternative is null");
		}
		this.alternative = alternative;
		return this;
	}

	@Override
	public void doHandle(MarkdomHandler<?> handler) {
		handler.onImageContent(uri, title, alternative);
	}

	@Override
	public int hashCode() {
		return ObjectHelper.hashCode(this, PROPERTIES);
	}

	@Override
	public boolean equals(Object object) {
		return ObjectHelper.equals(this, MarkdomImageContent.class, PROPERTIES, object);
	}

	@Override
	public String toString() {
		return ObjectHelper.toString(this, PROPERTIES);
	}

}
