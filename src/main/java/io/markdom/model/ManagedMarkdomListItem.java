package io.markdom.model;

import io.markdom.handler.MarkdomHandler;

public interface ManagedMarkdomListItem extends MarkdomListItem {

	void onAttach(MarkdomListBlock listBlock) throws IllegalArgumentException, IllegalStateException;

	void onDetach() throws IllegalStateException;

	void onHandle(MarkdomHandler<?> handler);

}