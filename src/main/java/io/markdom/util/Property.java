package io.markdom.util;

import java.util.function.Function;

import lombok.Data;
import lombok.ToString;

@Data
public final class Property<Entity, Value> {

	private final String name;

	@ToString.Exclude
	private final Function<Entity, Value> accessor;

}
