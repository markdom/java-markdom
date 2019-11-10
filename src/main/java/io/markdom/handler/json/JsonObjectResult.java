package io.markdom.handler.json;

@SuppressWarnings("hiding")
public interface JsonObjectResult<Object> {

	public Object asObject();

	public default String asObjectText() {
		return asObjectText(false);
	}

	public String asObjectText(boolean pretty);

}
