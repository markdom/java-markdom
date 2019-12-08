package io.markdom.handler.text.commonmark;

import lombok.SneakyThrows;

final class LinkBeginShred implements Shred {

	LinkBeginShred() {
	}
	
	
	@Override
	public ShredType getType() {
		return ShredType.LINK_BEGIN;
	}

	@Override
	public boolean isDelimiterBeginning() {
		return true;
	}

	@Override
	public boolean blocksSpace(Position position) {
		return Position.TRAILING == position;
	}

	@Override
	@SneakyThrows
	public void appendTo(LineAppendable appendable) {
		appendable.append("[");
	}

	@Override
	public String toString() {
		return "(" + "[" + ")";
	}



}