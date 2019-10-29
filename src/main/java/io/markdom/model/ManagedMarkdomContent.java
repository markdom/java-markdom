package io.markdom.model;

import io.markdom.handler.MarkdomHandler;

public interface ManagedMarkdomContent extends MarkdomContent {

	public void onAttach(MarkdomContentParent parent);

	public void onDetach();

	public void onHandle(MarkdomHandler<?> handler);

}