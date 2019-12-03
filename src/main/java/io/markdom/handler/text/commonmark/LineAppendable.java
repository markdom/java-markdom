package io.markdom.handler.text.commonmark;

public interface LineAppendable {

	public void startLine();

	public void append(char character);

	public void append(CharSequence characters);

	public void endLine();

}
