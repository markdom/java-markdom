package io.markdom.model;

import java.util.Optional;

import io.markdom.common.MarkdomContentType;
import io.markdom.model.selection.MarkdomContentSelection;

public interface MarkdomImageContent extends MarkdomContent {

	@Override
	public default MarkdomContentType getContentType() {
		return MarkdomContentType.IMAGE;
	}

	public String getUri();

	public MarkdomImageContent setUri(String uri);

	public Optional<String> getTitle();

	public MarkdomImageContent setTitle(Optional<String> title);

	public Optional<String> getAlternative();

	public MarkdomImageContent setAlternative(Optional<String> alternative);

	public default <Result> Result select(MarkdomContentSelection<Result> selection) {
		return selection.onImageContent(this);
	}

}
