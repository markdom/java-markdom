package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import io.markdom.common.MarkdomKeys;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomBlock;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomNode;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Property;

public final class BasicMarkdomDocument extends AbstractMarkdomNode implements MarkdomDocument {

	// @formatter:off
	private static final List<Property<MarkdomDocument>> PROPERTIES = new ArrayList<>(Arrays.asList(
		new Property<>(MarkdomKeys.BLOCKS, MarkdomDocument::getBlocks)
	));
	// @formatter:on	

	private final BasicMarkdomBlockParentDelegate delegate = new BasicMarkdomBlockParentDelegate(this);

	BasicMarkdomDocument(MarkdomFactory factory) {
		super(factory);
	}

	@Override
	public Optional<? extends MarkdomNode> getParent() {
		return Optional.empty();
	}

	@Override
	public Optional<Integer> getIndex() {
		return Optional.empty();
	}

	@Override
	public Optional<MarkdomDocument> getDocument() {
		return Optional.empty();
	}

	@Override
	public final List<MarkdomBlock> getBlocks() {
		return delegate.getBlocks();
	}

	@Override
	public final MarkdomDocument addBlock(MarkdomBlock block) {
		delegate.addBlock(block);
		return this;
	}

	@Override
	public final MarkdomDocument addBlocks(MarkdomBlock... blocks) {
		delegate.addBlocks(blocks);
		return this;
	}

	@Override
	public final MarkdomDocument addBlocks(Iterable<MarkdomBlock> blocks) {
		delegate.addBlocks(blocks);
		return this;
	}

	@Override
	public <Result> Result handle(MarkdomHandler<Result> handler) {
		ObjectHelper.notNull("handler", handler);
		handler.onDocumentBegin();
		delegate.onHandle(handler);
		handler.onDocumentEnd();
		return handler.getResult();
	}

	@Override
	public int hashCode() {
		return ObjectHelper.hashCode(this, PROPERTIES);
	}

	@Override
	public boolean equals(Object object) {
		return ObjectHelper.equals(this, MarkdomDocument.class, PROPERTIES, object);
	}

	@Override
	public String toString() {
		return ObjectHelper.toString(this, PROPERTIES);
	}

}
