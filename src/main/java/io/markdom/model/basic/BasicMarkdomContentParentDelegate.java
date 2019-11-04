package io.markdom.model.basic;

import java.util.Iterator;
import java.util.List;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.ManagedMarkdomContent;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomContentParent;

public final class BasicMarkdomContentParentDelegate {

	private final List<ManagedMarkdomContent> contents;

	BasicMarkdomContentParentDelegate(MarkdomContentParent parent) {
		this.contents = new BasicMarkdomContentList(parent);
	}

	public List<? extends MarkdomContent> getContents() {
		return contents;
	}

	public void addContent(MarkdomContent content) {
		contents.add((ManagedMarkdomContent) content);
	}

	public void addContents(MarkdomContent... contents) {
		if (null == contents) {
			throw new IllegalArgumentException("The given array of Markdom contents is null");
		}
		for (MarkdomContent content : contents) {
			addContent(content);
		}
	}

	public void addContents(Iterable<MarkdomContent> contents) {
		if (null == contents) {
			throw new IllegalArgumentException("The given iterable of Markdom contents is null");
		}
		for (MarkdomContent content : contents) {
			addContent(content);
		}
	}

	public void onHandle(MarkdomHandler<?> handler) {
		handler.onContentsBegin();
		Iterator<ManagedMarkdomContent> iterator = contents.iterator();
		if (iterator.hasNext()) {
			iterator.next().onHandle(handler);
			while (iterator.hasNext()) {
				handler.onNextContent();
				iterator.next().onHandle(handler);
			}
		}
		handler.onContentsEnd();
	}

	@Override
	public String toString() {
		return contents.toString();
	}

}
