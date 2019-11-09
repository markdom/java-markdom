package io.markdom.model;

import io.markdom.common.MarkdomContentType;
import io.markdom.model.selection.MarkdomContentSelection;

public interface MarkdomTextContent extends MarkdomContent {

	@Override
	public default MarkdomContentType getContentType() {
		return MarkdomContentType.TEXT;
	}

	public String getText();

	public MarkdomTextContent setText(String text);

	public default <Result> Result select(MarkdomContentSelection<Result> selection) {
		return selection.onTextContent(this);
	}

}
