package io.markdom.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.markdom.common.MarkdomNodeType;
import io.markdom.model.choice.MarkdomNodeChoice;
import io.markdom.model.selection.MarkdomNodeChoiceSelection;
import io.markdom.model.selection.MarkdomNodeSelection;

public interface MarkdomNode {

	public MarkdomNodeType getNodeType();

	public default boolean hasParent() {
		return getParent().isPresent();
	}

	public Optional<? extends MarkdomNode> getParent();

	public Optional<Integer> getIndex();

	public default boolean hasDocument() {
		return getDocument().isPresent();
	};

	public Optional<MarkdomDocument> getDocument();

	public default Boolean hasChildren() {
		return !getChildren().isEmpty();
	};

	public default Integer countCount() {
		return getChildren().size();
	};

	public default List<? extends MarkdomNode> getChildren() {
		return Collections.emptyList();
	};

	public MarkdomFactory getFactory();

	public default void choose(MarkdomNodeChoice choice) {
		select(new MarkdomNodeChoiceSelection(choice));
	}

	public <Result> Result select(MarkdomNodeSelection<Result> selection);

}
