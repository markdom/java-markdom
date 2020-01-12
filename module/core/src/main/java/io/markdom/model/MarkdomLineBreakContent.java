package io.markdom.model;

import io.markdom.common.MarkdomContentType;
import io.markdom.model.selection.MarkdomContentSelection;

public interface MarkdomLineBreakContent extends MarkdomContent {

	@Override
	public default MarkdomContentType getContentType() {
		return MarkdomContentType.LINE_BREAK;
	}

	public Boolean getHard();

	public MarkdomLineBreakContent setHard(Boolean hard);

	public default <Result> Result select(MarkdomContentSelection<Result> selection) {
		return selection.onLineBreakContent(this);
	}

}
