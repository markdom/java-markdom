package io.markdom.handler.text.commonmark;

import java.util.Iterator;

import io.markdom.util.StringUtil;

final class IndentedCodeBlockSection implements Section {

	private final String emptyIndentedCodeBlockComment;

	private final String code;

	IndentedCodeBlockSection(CommonmarkTextConfiguration configuration, String code) {
		emptyIndentedCodeBlockComment = configuration.getEmptyIndentedCodeBlockComment();
		this.code = code;
	}

	@Override
	public void appendTo(LineAppendable appendable) {
		Iterator<String> lines = StringUtil.splitLines(code);
		if (lines.hasNext()) {
			while (lines.hasNext()) {
				appendable.startLine();
				appendable.append("    ");
				appendable.append(lines.next());
				appendable.endLine();
			}
		} else {
			new CommentBlockSection(emptyIndentedCodeBlockComment).appendTo(appendable);
		}
	}

}