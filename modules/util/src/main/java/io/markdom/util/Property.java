package io.markdom.util;

import java.util.function.Function;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
public final class Property<Entity> {

	private final String name;

	@ToString.Exclude
	@Getter(AccessLevel.PRIVATE)
	private final Function<Entity, Object> accessor;

	public Object apply(Entity entity) {
		return accessor.apply(entity);
	}

}
