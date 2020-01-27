package io.markdom.util;

public interface NodeChoice {

	public void choose(Element element);

	public void choose(Text text);

	public void choose(CharacterData characterData);

	public void choose(Gap gap);

}
