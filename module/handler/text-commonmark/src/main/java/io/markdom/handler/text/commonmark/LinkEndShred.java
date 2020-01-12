package io.markdom.handler.text.commonmark;

import java.util.Optional;

import lombok.SneakyThrows;

final class LinkEndShred implements Shred {

	private String uri;

	private Optional<String> title;

	LinkEndShred(String uri, Optional<String> title) {
		this.uri = uri.trim();
		this.title = title.map(String::trim);
	}

	@Override
	public ShredType getType() {
		return ShredType.LINK_END;
	}

	@Override
	public boolean blocksSpace(Position position) {
		return Position.LEADING == position;
	}

	@Override
	@SneakyThrows
	public void appendTo(LineAppendable appendable) {
		appendable.append("](");
		appendable.append(uri);
		appendable.append(mapTitle().orElse(""));
		appendable.append(")");
	}

	private Optional<String> mapTitle() {
		return title.map(title -> title.isEmpty() ? "" : " " + title);
	}

	@Override
	public String toString() {
		return "(" + uri + ", " + title + ")";
	}

}
