package io.markdom.handler.text.commonmark;

import java.util.List;

import io.markdom.util.StringUtil;

final class CommentBlockSection implements Section {

	private final String comment;

	CommentBlockSection(String comment) {
		this.comment = comment;
	}

	@Override
	public void appendTo(LineAppendable appendable) {
		List<String> lines = StringUtil.splitLines(comment);
		if (lines.isEmpty()) {
			appendable.startLine();
			appendable.append("<!-- -->");
			appendable.endLine();
		} else if (1 == lines.size()) {
			appendable.startLine();
			appendable.append("<!-- ");
			appendable.append(lines.get(0));
			appendable.append(" -->");
			appendable.endLine();
		} else {
			appendable.startLine();
			appendable.append("<!--");
			appendable.endLine();
			for (String line : lines) {
				appendable.startLine();
				appendable.append("  ");
				appendable.append(line);
				appendable.endLine();
			}
			appendable.startLine();
			appendable.append("-->");
			appendable.endLine();
		}
	}

}