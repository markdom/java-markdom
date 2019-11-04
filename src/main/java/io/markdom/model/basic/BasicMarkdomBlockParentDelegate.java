package io.markdom.model.basic;

import java.util.Iterator;
import java.util.List;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.ManagedMarkdomBlock;
import io.markdom.model.MarkdomBlock;
import io.markdom.model.MarkdomBlockParent;

public final class BasicMarkdomBlockParentDelegate {

	private final List<ManagedMarkdomBlock> blocks;

	BasicMarkdomBlockParentDelegate(MarkdomBlockParent parent) {
		this.blocks = new BasicMarkdomBlockList(parent);
	}

	public List<? extends MarkdomBlock> getBlocks() {
		return blocks;
	}

	public void addBlock(MarkdomBlock block) {
		blocks.add((ManagedMarkdomBlock) block);
	}

	public void addBlocks(MarkdomBlock... blocks) {
		if (null == blocks) {
			throw new IllegalArgumentException("The given array of Markdom blocks is null");
		}
		for (MarkdomBlock block : blocks) {
			addBlock(block);
		}
	}

	public void addBlocks(Iterable<MarkdomBlock> blocks) {
		if (null == blocks) {
			throw new IllegalArgumentException("The given iterable of Markdom blocks is null");
		}
		for (MarkdomBlock block : blocks) {
			addBlock(block);
		}
	}

	public void onHandle(MarkdomHandler<?> handler) {
		handler.onBlocksBegin();
		Iterator<ManagedMarkdomBlock> iterator = blocks.iterator();
		if (iterator.hasNext()) {
			iterator.next().onHandle(handler);
			while (iterator.hasNext()) {
				handler.onNextBlock();
				iterator.next().onHandle(handler);
			}
		}
		handler.onBlocksEnd();
	}

	@Override
	public String toString() {
		return blocks.toString();
	}

}
