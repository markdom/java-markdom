package io.markdom.handler.text.commonmark;

import io.markdom.common.MarkdomEmphasisLevel;

interface Shred {

	public ShredType getType();

	public default boolean isDelimiterBeginning() {
		return false;
	}

	public default boolean isEmphasisBegin(MarkdomEmphasisLevel level) {
		return false;
	}

	public default boolean isEmphasisEnd(MarkdomEmphasisLevel level) {
		return false;
	}

	public default boolean isLineBeginning() {
		return false;
	}

	public default boolean isLineEnding() {
		return false;
	}

	public default boolean appendCode(String code) {
		return false;
	}

	public default boolean appendLineBreak(boolean hard) {
		return false;
	}

	public default boolean appendText(String text) {
		return false;
	}

	public default boolean blocksSpace(Position position) {
		return false;
	}

	public default boolean consumesSpace(Position position) {
		return false;
	}

	public default boolean hasSpace(Position position) {
		return false;
	}

	public default void setSpace(Position position, boolean space) {
	}

	public default void setHint(Position position, ShredType type) {
	}

	public default boolean isEmpty() {
		return false;
	}

	public void appendTo(LineAppendable appendable);

}