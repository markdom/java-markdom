package io.markdom.model.selection;

import io.markdom.model.MarkdomCodeContent;
import io.markdom.model.MarkdomEmphasisContent;
import io.markdom.model.MarkdomImageContent;
import io.markdom.model.MarkdomLineBreakContent;
import io.markdom.model.MarkdomLinkContent;
import io.markdom.model.MarkdomTextContent;

public abstract class AbstractMarkdomContentSelection<Result> implements MarkdomContentSelection<Result> {

	@Override
	public Result onCodeContent(MarkdomCodeContent codeContent) {
		return null;
	}

	@Override
	public Result onEmphasisContent(MarkdomEmphasisContent emphasisContent) {
		return null;
	}

	@Override
	public Result onImageContent(MarkdomImageContent imageContent) {
		return null;
	}

	@Override
	public Result onLineBreakContent(MarkdomLineBreakContent lineBreakContent) {
		return null;
	}

	@Override
	public Result onLinkContent(MarkdomLinkContent linkContent) {
		return null;
	}

	@Override
	public Result onTextContent(MarkdomTextContent textContent) {
		return null;
	}

}
