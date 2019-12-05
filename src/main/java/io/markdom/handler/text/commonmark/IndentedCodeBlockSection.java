package io.markdom.handler.text.commonmark;

import java.util.List;

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
		List<String> lines = StringUtil.splitLines(code);
		if (lines.isEmpty()) {
			new CommentBlockSection(emptyIndentedCodeBlockComment).appendTo(appendable);
		} else {
			for (String line : lines) {
				appendable.startLine();
				appendable.append("    ");
				appendable.append(line);
				appendable.endLine();
			}
		}
	}

}