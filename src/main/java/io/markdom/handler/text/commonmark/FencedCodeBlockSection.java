package io.markdom.handler.text.commonmark;

import java.util.Iterator;
import java.util.Optional;

import io.markdom.util.StringUtil;

final class FencedCodeBlockSection implements Section {

	private final String fence;

	private final String code;

	private final Optional<String> hint;

	public FencedCodeBlockSection(CommonmarkTextOptions options, String code, Optional<String> hint) {
		char fenceCharacter = options.getCodeFenceOption().getFenceCharacter();
		int fenceLength = Math.max(3, StringUtil.longestSequenceLength(code, fenceCharacter) + 1);
		this.fence = StringUtil.repeat(fenceCharacter, fenceLength);
		this.code = code;
		this.hint = hint;
	}

	@Override
	public void appendTo(LineAppendable sink) {
		appendLeadingFence(sink);
		appendCode(sink);
		appendTrailingFence(sink);
	}

	private void appendLeadingFence(LineAppendable sink) {
		sink.startLine();
		sink.append(fence);
		hint.ifPresent(hintString -> {
			sink.append(' ');
			sink.append(hintString.trim());
		});
		sink.endLine();
	}

	private void appendCode(LineAppendable sink) {
		Iterator<String> lines = StringUtil.splitLines(code);
		while (lines.hasNext()) {
			sink.startLine();
			sink.append(lines.next());
			sink.endLine();
		}
	}

	private void appendTrailingFence(LineAppendable sink) {
		sink.startLine();
		sink.append(fence);
		sink.endLine();
	}

}
