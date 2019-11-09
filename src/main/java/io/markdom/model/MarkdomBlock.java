package io.markdom.model;

import java.util.Optional;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomNodeType;
import io.markdom.model.choice.MarkdomBlockChoice;
import io.markdom.model.selection.MarkdomBlockChoiceSelection;
import io.markdom.model.selection.MarkdomBlockSelection;
import io.markdom.model.selection.MarkdomNodeSelection;

public interface MarkdomBlock extends MarkdomNode {

	@Override
	public default MarkdomNodeType getNodeType() {
		return MarkdomNodeType.BLOCK;
	}

	public MarkdomBlockType getBlockType();

	@Override
	public Optional<MarkdomBlockParent> getParent();

	@Override
	public default <Result> Result select(MarkdomNodeSelection<Result> selection) {
		return selection.onBlock(this);
	}

	public default void choose(MarkdomBlockChoice choice) {
		select(new MarkdomBlockChoiceSelection(choice));
	}

	public <Result> Result select(MarkdomBlockSelection<Result> selection);

}
