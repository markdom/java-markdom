package io.markdom.util;

import java.util.function.Consumer;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FunctionUtil {

	private static final Consumer<?> IDLE_CONSUMER = value -> {
	};

	@SuppressWarnings("unchecked")
	public static final <Value> Consumer<Value> idleConsumer() {
		return (Consumer<Value>) IDLE_CONSUMER;
	}
}
