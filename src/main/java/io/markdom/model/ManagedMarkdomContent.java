package io.markdom.model;

import io.markdom.handler.MarkdomHandler;

public interface ManagedMarkdomContent extends MarkdomContent {

	public Runnable onAttach(MarkdomContentParent parent);

	public Runnable onDetach(MarkdomContentParent parent);

	public void onHandle(MarkdomHandler<?> handler);

}