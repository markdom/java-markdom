package io.markdom.model;

import java.util.List;

import io.markdom.common.MarkdomBlockParentType;
import io.markdom.model.choice.MarkdomBlockParentChoice;
import io.markdom.model.selection.MarkdomBlockParentChoiceSelection;
import io.markdom.model.selection.MarkdomBlockParentSelection;

public interface MarkdomBlockParent extends MarkdomNode {

	public MarkdomBlockParentType getBlockParentType();

	public List<MarkdomBlock> getBlocks();

	public MarkdomBlockParent addBlock(MarkdomBlock block);

	public MarkdomBlockParent addBlocks(MarkdomBlock... blocks);

	public MarkdomBlockParent addBlocks(Iterable<MarkdomBlock> blocks);

	@Override
	public default List<MarkdomBlock> getChildren() {
		return getBlocks();
	}

	public default void choose(MarkdomBlockParentChoice choice) {
		select(new MarkdomBlockParentChoiceSelection(choice));
	}

	public <Result> Result select(MarkdomBlockParentSelection<Result> selection);

}
