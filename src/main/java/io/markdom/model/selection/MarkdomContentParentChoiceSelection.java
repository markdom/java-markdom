package io.markdom.model.selection;

import io.markdom.model.MarkdomEmphasisContent;
import io.markdom.model.MarkdomHeadingBlock;
import io.markdom.model.MarkdomLinkContent;
import io.markdom.model.MarkdomParagraphBlock;
import io.markdom.model.choice.MarkdomContentParentChoice;
import io.markdom.util.ObjectHelper;

public final class MarkdomContentParentChoiceSelection extends AbstractMarkdomContentParentSelection<Void> {

	private final MarkdomContentParentChoice choice;

	public MarkdomContentParentChoiceSelection(MarkdomContentParentChoice choice) {
		this.choice = ObjectHelper.notNull("choice", choice);
	}

	@Override
	public Void onHeadingBlock(MarkdomHeadingBlock headingBlock) {
		choice.onHeadingBlock(headingBlock);
		return null;
	}

	@Override
	public Void onParagraphBlock(MarkdomParagraphBlock paragraphBlock) {
		choice.onParagraphBlock(paragraphBlock);
		return null;
	}

	@Override
	public Void onEmphasisContent(MarkdomEmphasisContent emphasisContent) {
		choice.onEmphasisContent(emphasisContent);
		return null;
	}

	@Override
	public Void onLinkContent(MarkdomLinkContent linkContent) {
		choice.onLinkContent(linkContent);
		return null;
	}

}