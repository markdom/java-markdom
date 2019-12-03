package io.markdom.handler.text.commonmark;

import io.markdom.util.StringUtil;

final class OrderedListIndentation implements Indentation {

	private final char commitCharacter;

	private int nextIndex;

	private String followingString;

	OrderedListIndentation(CommonmarkTextConfiguration configuration, Integer startIndex) {
		this.commitCharacter = configuration.getOrderedListOption().getCommitCharacter();
		this.nextIndex = startIndex;
	}

	@Override
	public void advance() {
		followingString = null;
	}

	@Override
	public String get() {
		if (null != followingString) {
			return followingString;
		} else {
			String indexString = Integer.toString(nextIndex++);
			followingString = StringUtil.repeat(' ', indexString.length() + 2);
			return indexString + commitCharacter + " ";
		}

	}

}
