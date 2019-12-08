package io.markdom.handler.text.commonmark;

import java.util.List;
import java.util.ListIterator;

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
		List<String> lines = removeLeadingAndTrailingEmptyLines(StringUtil.splitLines(code));
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

	private List<String> removeLeadingAndTrailingEmptyLines(List<String> lines) {
		return removeTrailingEmptyLines(removeLeadingEmptyLines(lines));
	}

	private List<String> removeLeadingEmptyLines(List<String> lines) {
		ListIterator<String> iterator = lines.listIterator();
		while (iterator.hasNext() && iterator.next().trim().isEmpty()) {
			iterator.remove();
		}
		return lines;
	}

	private List<String> removeTrailingEmptyLines(List<String> lines) {
		ListIterator<String> iterator = lines.listIterator(lines.size());
		while (iterator.hasPrevious() && iterator.previous().trim().isEmpty()) {
			iterator.remove();
		}
		return lines;
	}

}