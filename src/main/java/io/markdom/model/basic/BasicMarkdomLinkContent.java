package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import io.markdom.common.MarkdomKeys;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomLinkContent;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Property;

public final class BasicMarkdomLinkContent extends AbstractMarkdomContent implements MarkdomLinkContent {

	// @formatter:off
	private static final List<Property<MarkdomLinkContent, ?>> PROPERTIES = new ArrayList<>(Arrays.asList(
		new Property<>(MarkdomKeys.URI, MarkdomLinkContent::getUri),
		new Property<>(MarkdomKeys.TITLE, MarkdomLinkContent::getTitle),
		new Property<>(MarkdomKeys.CONTENTS, MarkdomLinkContent::getContents)
	));
	// @formatter:on	

	private final BasicMarkdomContentParentDelegate delegate = new BasicMarkdomContentParentDelegate(this);

	private String uri;

	private Optional<String> title;

	BasicMarkdomLinkContent(MarkdomFactory factory, String uri, Optional<String> title) {
		super(factory);
		setUri(uri);
		setTitle(title);
	}

	@Override
	public String getUri() {
		return uri;
	}

	public BasicMarkdomLinkContent setUri(String uri) {
		this.uri = ObjectHelper.notNull("uri", uri);
		return this;
	}

	@Override
	public Optional<String> getTitle() {
		return title;
	}

	public BasicMarkdomLinkContent setTitle(Optional<String> title) {
		this.title = ObjectHelper.notNull("optional title", title);
		return this;
	}

	@Override
	public List<? extends MarkdomContent> getContents() {
		return delegate.getContents();
	}

	@Override
	public MarkdomLinkContent addContent(MarkdomContent content) {
		delegate.addContent(content);
		return this;
	}

	@Override
	public MarkdomLinkContent addContents(MarkdomContent... contents) {
		delegate.addContents(contents);
		return this;
	}

	@Override
	public MarkdomLinkContent addContents(Iterable<MarkdomContent> contents) {
		delegate.addContents(contents);
		return this;
	}

	@Override
	protected void doHandle(MarkdomHandler<?> handler) {
		handler.onLinkContentBegin(uri, title);
		delegate.onHandle(handler);
		handler.onLinkContentEnd(uri, title);
	}

	@Override
	public int hashCode() {
		return ObjectHelper.hashCode(this, PROPERTIES);
	}

	@Override
	public boolean equals(Object object) {
		return ObjectHelper.equals(this, MarkdomLinkContent.class, PROPERTIES, object);
	}

	@Override
	public String toString() {
		return ObjectHelper.toString(this, PROPERTIES);
	}

}
