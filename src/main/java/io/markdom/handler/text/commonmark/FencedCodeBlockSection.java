package io.markdom.handler.text.commonmark;

import java.util.List;
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
		List<String> lines = StringUtil.splitLines(code);
		for (String line : lines) {
			appendable.startLine();
			appendable.append(line);
			appendable.endLine();
		}
	}

	private void appendTrailingFence(LineAppendable appendable) {
		appendable.startLine();
		appendable.append(fence);
		appendable.endLine();
	}

}
