package io.markdom.handler.text.commonmark;

import java.util.Iterator;

import io.markdom.util.StringUtil;

final class CommentBlockSection implements Section {

	private final String comment;

	CommentBlockSection(String comment) {
		this.comment = comment;
	}

	@Override
	public void appendTo(LineAppendable appendable) {
		Iterator<String> lines = StringUtil.splitLines(comment);
		appendable.startLine();
		appendable.append("<!--");
		if (!lines.hasNext()) {
			appendable.append(" ");
		} else {
			String first = lines.next();
			if (!lines.hasNext()) {
				appendable.append(" ");
				appendable.append(first.trim());
				appendable.append(" ");
			} else {
				appendable.endLine();
				appendable.startLine();
				appendable.append(first);
				appendable.endLine();
				while (lines.hasNext()) {
					appendable.startLine();
					appendable.append(lines.next());
					appendable.endLine();
				}
				appendable.startLine();
			}
		}
		appendable.append("-->");
		appendable.endLine();
	}

}