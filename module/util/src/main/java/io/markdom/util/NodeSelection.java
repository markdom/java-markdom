package io.markdom.util;

public interface NodeSelection<Result> {

	public Result select(Element element);

	public Result select(Text text);

	public Result select(CharacterData characterData);

	public Result select(Gap gap);

}
