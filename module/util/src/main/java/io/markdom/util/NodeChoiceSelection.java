package io.markdom.util;

public final class NodeChoiceSelection implements NodeSelection<Void> {

	private final NodeChoice choice;

	public NodeChoiceSelection(NodeChoice choice) {
		this.choice = ObjectHelper.notNull("choice", choice);
	}

	@Override
	public Void select(Element element) {
		choice.choose(element);
		return null;
	}

	@Override
	public Void select(Text text) {
		choice.choose(text);
		return null;
	}
	
	@Override
	public Void select(CharacterData characterData) {
		choice.choose(characterData);
		return null;
	}

	@Override
	public Void select(Gap gap) {
		choice.choose(gap);
		return null;
	}



}
