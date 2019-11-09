package io.markdom.util;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectHelper {

	public static <Entity> int hashCode(Entity entity, List<Property<Entity, ?>> properties) {
		return Objects.hash(mapProperties(entity, properties));
	}

	private static <Entity> Object[] mapProperties(Entity entity, List<Property<Entity, ?>> properties) {
		Object[] objects = new Object[properties.size()];
		for (int i = 0, n = properties.size(); i < n; i++) {
			objects[i] = properties.get(i).getAccessor().apply(entity);
		}
		return objects;
	}

	@SuppressWarnings("unchecked")
	public static <Entity> boolean equals(Entity entity, Class<Entity> type, List<Property<Entity, ?>> properties, Object object) {
		if (entity == object) {
			return true;
		} else if (null == object) {
			return false;
		} else if (!type.isInstance(object)) {
			return false;
		}
		Entity other = (Entity) object;
		for (Property<Entity, ?> property : properties) {
			Function<Entity, ?> accessor = property.getAccessor();
			if (!Objects.equals(accessor.apply(entity), property.getAccessor().apply(other))) {
				return false;
			}
		}
		return true;
	}

	public static <Entity> String toString(Entity entity, List<Property<Entity, ?>> properties) {
		StringBuilder builder = new StringBuilder(entity.getClass().getSimpleName()).append(" [");
		Iterator<Property<Entity, ?>> iterator = properties.iterator();
		if (iterator.hasNext()) {
			Property<Entity, ?> first = iterator.next();
			builder.append(first.getName()).append("=").append(first.getAccessor().apply(entity));
			while (iterator.hasNext()) {
				Property<Entity, ?> following = iterator.next();
				builder.append(", ").append(following.getName()).append("=").append(following.getAccessor().apply(entity));
			}
		}
		builder.append("]");
		return builder.toString();
	}
}
