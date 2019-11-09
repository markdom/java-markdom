package io.markdom.model;

import io.markdom.common.MarkdomContentType;
import io.markdom.model.selection.MarkdomContentSelection;

public interface MarkdomCodeContent extends MarkdomContent {

	@Override
	public default MarkdomContentType getContentType() {
		return MarkdomContentType.CODE;
	}

	public String getCode();

	public MarkdomCodeContent setCode(String code);

	public default <Result> Result select(MarkdomContentSelection<Result> selection) {
		return selection.onCodeContent(this);
	}

}
