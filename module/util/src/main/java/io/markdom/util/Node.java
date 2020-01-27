package io.markdom.util;

public interface Node {

	public default void choose(NodeChoice choice) {
		select(new NodeChoiceSelection(choice));
	}

	public <Result> Result select(NodeSelection<Result> selection);

}
