package io.markdom.model.choice;

import io.markdom.model.MarkdomCodeContent;
import io.markdom.model.MarkdomEmphasisContent;
import io.markdom.model.MarkdomImageContent;
import io.markdom.model.MarkdomLineBreakContent;
import io.markdom.model.MarkdomLinkContent;
import io.markdom.model.MarkdomTextContent;

public abstract class AbstractMarkdomContentChoice implements MarkdomContentChoice {

	@Override
	public void onCodeContent(MarkdomCodeContent codeContent) {
	}

	@Override
	public void onEmphasisContent(MarkdomEmphasisContent emphasisContent) {
	}

	@Override
	public void onImageContent(MarkdomImageContent imageContent) {
	}

	@Override
	public void onLineBreakContent(MarkdomLineBreakContent lineBreakContent) {
	}

	@Override
	public void onLinkContent(MarkdomLinkContent linkContent) {
	}

	@Override
	public void onTextContent(MarkdomTextContent textContent) {
	}

}
