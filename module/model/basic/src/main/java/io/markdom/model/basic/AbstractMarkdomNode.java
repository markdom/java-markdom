package io.markdom.model.basic;

import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomNode;

abstract class AbstractMarkdomNode implements MarkdomNode {

	private final MarkdomFactory factory;

	AbstractMarkdomNode(MarkdomFactory factory) {
		this.factory = factory;
	}

	@Override
	public final MarkdomFactory getFactory() {
		return factory;
	}

}
