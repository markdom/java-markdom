package io.markdom.model;

import io.markdom.handler.MarkdomHandler;

public interface ManagedMarkdomListItem extends MarkdomListItem {

	public Runnable onAttach(MarkdomListBlock listBlock);

	public Runnable onDetach(MarkdomListBlock listBlock);

	public void onHandle(MarkdomHandler<?> handler);

}