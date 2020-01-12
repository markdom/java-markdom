package io.markdom.handler;

public interface MarkdomDispatcher {

	public <Result> Result handle(MarkdomHandler<Result> handler);

}
