package io.markdom.handler.text.commonmark;

import java.util.Iterator;

import io.markdom.util.StringUtil;

final class IndentedCodeBlockSection implements Section {

	private final String code;

	IndentedCodeBlockSection(String code) {
		this.code = code;
	}

	@Override
	public void appendTo(LineAppendable sink) {
		Iterator<String> lines = StringUtil.splitLines(code);
		while (lines.hasNext()) {
			sink.startLine();
			sink.append("    ");
			sink.append(lines.next());
			sink.endLine();
		}
	}

}