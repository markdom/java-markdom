package io.markdom.util;

import lombok.Data;

@Data
public final class Gap implements Node {

	public Gap() {
	}

	@Override
	public <Result> Result select(NodeSelection<Result> selection) {
		return selection.select(this);
	}

}