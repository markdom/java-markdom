package io.markdom.model.basic;

import java.util.List;
import java.util.Optional;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomLinkContent;

public final class BasicMarkdomLinkContent extends AbstractMarkdomContent implements MarkdomLinkContent {

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

	public BasicMarkdomLinkContent setTitle(Optional<String> title) {
		if (null == title) {
			throw new IllegalArgumentException("The given title is null");
		}
		this.title = title;
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
	public String toString() {
		return getClass().getSimpleName() + " [uri=" + uri + ", title=" + title + ", content=" + getContents() + "]";
	}

}
