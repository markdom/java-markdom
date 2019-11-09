package io.markdom.model.selection;

import io.markdom.model.MarkdomCodeContent;
import io.markdom.model.MarkdomEmphasisContent;
import io.markdom.model.MarkdomImageContent;
import io.markdom.model.MarkdomLineBreakContent;
import io.markdom.model.MarkdomLinkContent;
import io.markdom.model.MarkdomTextContent;
import io.markdom.model.choice.MarkdomContentChoice;

public final class MarkdomContentChoiceSelection extends AbstractMarkdomContentSelection<Void> {

	private final MarkdomContentChoice choice;

	public MarkdomContentChoiceSelection(MarkdomContentChoice choice) {
		if (null == choice) {
			throw new IllegalArgumentException("The given choice is null");
		}
		this.choice = choice;
	}

	@Override
	public Void onCodeContent(MarkdomCodeContent codeContent) {
		choice.onCodeContent(codeContent);
		return null;
	}

	@Override
	public Void onEmphasisContent(MarkdomEmphasisContent emphasisContent) {
		choice.onEmphasisContent(emphasisContent);
		return null;
	}

	@Override
	public Void onImageContent(MarkdomImageContent imageContent) {
		choice.onImageContent(imageContent);
		return null;
	}

	@Override
	public Void onLineBreakContent(MarkdomLineBreakContent lineBreakContent) {
		choice.onLineBreakContent(lineBreakContent);
		return null;
	}

	@Override
	public Void onLinkContent(MarkdomLinkContent linkContent) {
		choice.onLinkContent(linkContent);
		return null;
	}

	@Override
	public Void onTextContent(MarkdomTextContent textContent) {
		choice.onTextContent(textContent);
		return null;
	}
	
}