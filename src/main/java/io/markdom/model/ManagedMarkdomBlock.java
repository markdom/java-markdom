package io.markdom.model;

import io.markdom.handler.MarkdomHandler;

public interface ManagedMarkdomBlock extends MarkdomBlock {

	public Runnable onAttach(MarkdomBlockParent parent);

	public Runnable onDetach(MarkdomBlockParent parent);

	public void onHandle(MarkdomHandler<?> handler);

}