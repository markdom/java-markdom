package io.markdom.model;

import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomNodeType;
import io.markdom.model.choice.MarkdomContentChoice;
import io.markdom.model.selection.MarkdomContentChoiceSelection;
import io.markdom.model.selection.MarkdomContentSelection;
import io.markdom.model.selection.MarkdomNodeSelection;

public interface MarkdomContent extends MarkdomNode {

	@Override
	public default MarkdomNodeType getNodeType() {
		return MarkdomNodeType.CONTENT;
	}

	public MarkdomContentType getContentType();

	@Override
	public default <Result> Result select(MarkdomNodeSelection<Result> selection) {
		return selection.onContent(this);
	}

	public default void choose(MarkdomContentChoice choice) {
		select(new MarkdomContentChoiceSelection(choice));
	}

	public <Result> Result select(MarkdomContentSelection<Result> selection);

}
