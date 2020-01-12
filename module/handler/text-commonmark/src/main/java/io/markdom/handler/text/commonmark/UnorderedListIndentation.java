package io.markdom.handler.text.commonmark;

final class UnorderedListIndentation implements Indentation {

	private final String bulletString;

	private boolean following;

	UnorderedListIndentation(CommonmarkTextConfiguration configuration) {
		bulletString = configuration.getUnorderedListOption().getBulletCharacter() + " ";
	}

	@Override
	public void advance() {
		following = false;
	}

	@Override
	public String get() {
		if (following) {
			return "  ";
		} else {
			following = true;
			return bulletString;
		}
	}

}
