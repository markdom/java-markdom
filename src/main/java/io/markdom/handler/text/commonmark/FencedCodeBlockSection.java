package io.markdom.handler.text.commonmark;

import java.util.Iterator;
import java.util.Optional;

import io.markdom.util.StringUtil;

final class FencedCodeBlockSection implements Section {

	private final String fence;

	private final String code;

	private final Optional<String> hint;

	public FencedCodeBlockSection(CommonmarkTextConfiguration configuration, String code, Optional<String> hint) {
		char fenceCharacter = configuration.getCodeFenceOption().getFenceCharacter();
		int fenceLength = Math.max(3, StringUtil.longestSequenceLength(code, fenceCharacter) + 1);
		this.fence = StringUtil.repeat(fenceCharacter, fenceLength);
		this.code = code;
		this.hint = hint;
	}

	@Override
	public void appendTo(LineAppendable appendable) {
		appendLeadingFence(appendable);
		appendCode(appendable);
		appendTrailingFence(appendable);
	}

	private void appendLeadingFence(LineAppendable appendable) {
		appendable.startLine();
		appendable.append(fence);
		hint.ifPresent(hintString -> {
			appendable.append(' ');
			appendable.append(hintString.trim());
		});
		appendable.endLine();
	}

	private void appendCode(LineAppendable appendable) {
		Iterator<String> lines = StringUtil.splitLines(code);
		while (lines.hasNext()) {
			appendable.startLine();
			appendable.append(lines.next());
			appendable.endLine();
		}
	}

	private void appendTrailingFence(LineAppendable appendable) {
		appendable.startLine();
		appendable.append(fence);
		appendable.endLine();
	}

}
