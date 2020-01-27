package io.markdom.util;

import lombok.Data;

@Data
public final class CharacterData implements Node {

	private final String text;

	public CharacterData(String text) {
		this.text = ObjectHelper.notNull("text", text);
	}

	@Override
	public <Result> Result select(NodeSelection<Result> selection) {
		return selection.select(this);
	}

}