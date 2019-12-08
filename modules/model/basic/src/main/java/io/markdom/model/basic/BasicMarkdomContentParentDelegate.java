package io.markdom.model.basic;

import java.util.Iterator;
import java.util.List;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomContent;
import io.markdom.model.MarkdomContentParent;
import io.markdom.util.ObjectHelper;

public final class BasicMarkdomContentParentDelegate {

	private final BasicMarkdomContentList contents;

	BasicMarkdomContentParentDelegate(MarkdomContentParent parent) {
		this.contents = new BasicMarkdomContentList(parent);
	}

	public List<MarkdomContent> getContents() {
		return contents;
	}

	public void addContent(MarkdomContent content) {
		contents.add(content);
	}

	public void addContents(MarkdomContent... contents) {
		for (MarkdomContent content : ObjectHelper.notNull("array of contents", contents)) {
			addContent(content);
		}
	}

	public void addContents(Iterable<MarkdomContent> contents) {
		for (MarkdomContent content : ObjectHelper.notNull("iterable of contents", contents)) {
			addContent(content);
		}
	}

	public void onHandle(MarkdomHandler<?> handler) {
		handler.onContentsBegin();
		Iterator<ManagedMarkdomContent> iterator = contents.internalIterator();
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
