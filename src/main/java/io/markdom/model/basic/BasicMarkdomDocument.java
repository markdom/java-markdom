package io.markdom.model.basic;

import java.util.List;
import java.util.Optional;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomBlock;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomNode;

public final class BasicMarkdomDocument extends AbstractMarkdomNode implements MarkdomDocument {

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
	public final List<? extends MarkdomBlock> getBlocks() {
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
		if (null == handler) {
			throw new IllegalArgumentException("The given Markdom handler is null");
		}
		handler.onDocumentBegin();
		delegate.onHandle(handler);
		handler.onDocumentEnd();
		return handler.getResult();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [blocks=" + getBlocks() + "]";
	}

}
