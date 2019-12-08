package io.markdom.handler.text.commonmark;

import java.util.Optional;

import lombok.SneakyThrows;

final class ImageShred implements Shred {

	private String uri;

	private Optional<String> title;

	private Optional<String> alternative;

	ImageShred(String uri, Optional<String> title, Optional<String> alternative) {
		this.uri = uri.trim();
		this.title = title.map(String::trim);
		this.alternative = alternative.map(String::trim);
	}

	@Override
	public ShredType getType() {
		return ShredType.IMAGE;
	}

	@Override
	public boolean blocksSpace(Position position) {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return uri.isEmpty();
	}

	@Override
	@SneakyThrows
	public void appendTo(LineAppendable appendable) {
		appendable.append("![");
		appendable.append(alternative.orElse(""));
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
		return "(" + uri + ", " + title + ", " + alternative + ")";
	}

}
