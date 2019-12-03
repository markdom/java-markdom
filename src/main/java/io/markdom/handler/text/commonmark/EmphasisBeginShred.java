package io.markdom.handler.text.commonmark;

import io.markdom.common.MarkdomEmphasisLevel;
import lombok.SneakyThrows;

final class EmphasisBeginShred implements Shred {

	private MarkdomEmphasisLevel level;

	private String bounderyString;

	EmphasisBeginShred(MarkdomEmphasisLevel level, String bounderyString) {
		this.level = level;
		this.bounderyString = bounderyString;
	}
	
	@Override
	public ShredType getType() {
		return ShredType.EMPHASIS_BEGIN;
	}


	@Override
	public boolean isDelimiterBeginning() {
		return true;
	}

	@Override
	public boolean isEmphasisBegin(MarkdomEmphasisLevel level) {
		return level == this.level;
	}

	@Override
	public boolean blocksSpace(Position position) {
		return Position.TRAILING == position;
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
