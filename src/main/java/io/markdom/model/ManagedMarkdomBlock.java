package io.markdom.model;

import io.markdom.handler.MarkdomHandler;

public interface ManagedMarkdomBlock extends MarkdomBlock {

	public void onAttach(MarkdomBlockParent parent);

	public void onDetach();

	public void onHandle(MarkdomHandler<?> handler);

}