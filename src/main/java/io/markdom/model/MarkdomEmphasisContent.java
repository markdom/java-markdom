package io.markdom.model;

import io.markdom.common.MarkdomContentParentType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.model.selection.MarkdomContentParentSelection;
import io.markdom.model.selection.MarkdomContentSelection;

public interface MarkdomEmphasisContent extends MarkdomContentParentContent {

	@Override
	public default MarkdomContentParentType getContentParentType() {
		return MarkdomContentParentType.EMPHASIS_CONTENT;
	}

	@Override
	public default MarkdomContentType getContentType() {
		return MarkdomContentType.EMPHASIS;
	}

	public MarkdomEmphasisLevel getLevel();

	public MarkdomEmphasisContent setLevel(MarkdomEmphasisLevel level);

	public MarkdomEmphasisContent addContent(MarkdomContent content);

	public MarkdomEmphasisContent addContents(MarkdomContent... contents);

	public MarkdomEmphasisContent addContents(Iterable<MarkdomContent> contents);

	public default <Result> Result select(MarkdomContentSelection<Result> selection) {
		return selection.onEmphasisContent(this);
	}

	@Override
	public default <Result> Result select(MarkdomContentParentSelection<Result> selection) {
		return selection.onEmphasisContent(this);
	}

}
