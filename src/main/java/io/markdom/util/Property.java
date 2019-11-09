package io.markdom.util;

import java.util.function.Function;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
public final class Property<Entity, Value> {

	private final String name;

	@ToString.Exclude
	@Getter(AccessLevel.PRIVATE)
	private final Function<Entity, Value> accessor;

	public Value apply(Entity entity) {
		return accessor.apply(entity);
	}

}
