package io.markdom.handler.text.commonmark;

import java.util.Stack;

public final class IndentationStack implements IndentationSupplier {

	private final Stack<Indentation> indentations = new Stack<>();

	IndentationStack() {
	}

	public void push(Indentation indentation) {
		indentations.push(indentation);
	}

	public void advance() {
		indentations.peek().advance();
	}

	public void pop() {
		indentations.pop();
	}

	@Override
	public String get() {
		StringBuilder builder = new StringBuilder();
		for (Indentation indentation : indentations) {
			builder.append(indentation.get());
		}
		return builder.toString();
	}

}
