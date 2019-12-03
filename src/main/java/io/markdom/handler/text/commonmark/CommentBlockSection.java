package io.markdom.handler.text.commonmark;

import java.util.Iterator;

import io.markdom.util.StringUtil;

final class CommentBlockSection implements Section {

	private final String comment;

	CommentBlockSection(String comment) {
		this.comment = comment;
	}

	@Override
	public void appendTo(LineAppendable sink) {
		Iterator<String> lines = StringUtil.splitLines(comment);
		sink.startLine();
		sink.append("<!--");
		if (!lines.hasNext()) {
			sink.append(" ");
		} else {
			String first = lines.next();
			if (!lines.hasNext()) {
				sink.append(" ");
				sink.append(first.trim());
				sink.append(" ");
			} else {
				sink.endLine();
				sink.startLine();
				sink.append(first);
				sink.endLine();
				while (lines.hasNext()) {
					sink.startLine();
					sink.append(lines.next());
					sink.endLine();
				}
				sink.startLine();
			}
		}
		sink.append("-->");
		sink.endLine();
	}

}