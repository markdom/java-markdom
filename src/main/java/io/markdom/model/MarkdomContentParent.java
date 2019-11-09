package io.markdom.model;

import java.util.Collections;
import java.util.List;

import io.markdom.common.MarkdomContentParentType;
import io.markdom.model.choice.MarkdomContentParentChoice;
import io.markdom.model.selection.MarkdomContentParentChoiceSelection;
import io.markdom.model.selection.MarkdomContentParentSelection;

public interface MarkdomContentParent extends MarkdomNode {

	public MarkdomContentParentType getContentParentType();

	public List<? extends MarkdomContent> getContents();

	public MarkdomContentParent addContent(MarkdomContent content);

	public MarkdomContentParent addContents(MarkdomContent... contents);

	public MarkdomContentParent addContents(Iterable<MarkdomContent> contents);

	@Override
	public default List<MarkdomContent> getChildren() {
		return Collections.unmodifiableList(getContents());
	}

	public default void choose(MarkdomContentParentChoice choice) {
		select(new MarkdomContentParentChoiceSelection(choice));
	}

	public <Result> Result select(MarkdomContentParentSelection<Result> selection);

}
