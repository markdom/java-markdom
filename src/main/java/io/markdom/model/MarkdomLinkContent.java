package io.markdom.model;

import java.util.Optional;

import io.markdom.common.MarkdomContentParentType;
import io.markdom.common.MarkdomContentType;
import io.markdom.model.selection.MarkdomContentParentSelection;
import io.markdom.model.selection.MarkdomContentSelection;

public interface MarkdomLinkContent extends MarkdomContentParentContent {

	@Override
	public default MarkdomContentParentType getContentParentType() {
		return MarkdomContentParentType.LINK_CONTENT;
	}

	@Override
	public default MarkdomContentType getContentType() {
		return MarkdomContentType.LINK;
	}

	public String getUri();

	public MarkdomLinkContent setUri(String uri);

	public Optional<String> getTitle();

	public MarkdomLinkContent setTitle(Optional<String> title);

	@Override
	public MarkdomLinkContent addContent(MarkdomContent content);

	@Override
	public MarkdomLinkContent addContents(MarkdomContent... contents);

	@Override
	public MarkdomLinkContent addContents(Iterable<MarkdomContent> contents);

	public default <Result> Result select(MarkdomContentSelection<Result> selection) {
		return selection.onLinkContent(this);
	}

	@Override
	public default <Result> Result select(MarkdomContentParentSelection<Result> selection) {
		return selection.onLinkContent(this);
	}

}
