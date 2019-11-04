package io.markdom.model.basic;

import java.util.Objects;
import java.util.Optional;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomImageContent;

public final class BasicMarkdomImageContent extends AbstractMarkdomContent implements MarkdomImageContent {

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
		return Objects.hash(uri, title, alternative);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (null == object) {
			return false;
		} else if (!(object instanceof MarkdomImageContent)) {
			return false;
		}
		MarkdomImageContent other = (MarkdomImageContent) object;
		if (!Objects.equals(uri, other.getUri())) {
			return false;
		} else if (!Objects.equals(title, other.getTitle())) {
			return false;
		} else if (!Objects.equals(alternative, other.getAlternative())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [uri=" + uri + ", title=" + title + ", alternative=" + alternative + "]";
	}

}
