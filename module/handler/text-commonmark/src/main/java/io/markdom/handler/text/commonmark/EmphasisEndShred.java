package io.markdom.handler.text.commonmark;

import io.markdom.common.MarkdomEmphasisLevel;
import lombok.SneakyThrows;

final class EmphasisEndShred implements Shred {

	private MarkdomEmphasisLevel level;

	private String bounderyString;

	EmphasisEndShred(MarkdomEmphasisLevel level, String bounderyString) {
		this.level = level;
		this.bounderyString = bounderyString;
	}

	@Override
	public ShredType getType() {
		return ShredType.EMPHASIS_END;
	}

	@Override
	public boolean isEmphasisEnd(MarkdomEmphasisLevel level) {
		return level == this.level;
	}

	@Override
	public boolean blocksSpace(Position position) {
		return Position.LEADING == position;
	}

	@Override
	@SneakyThrows
	public void appendTo(LineAppendable appendable) {
		appendable.append(bounderyString);
	}

	@Override
	public String toString() {
		return "(" + bounderyString + ")";
	}

}
