package io.markdom.handler.commonmark.atlassian;

import org.commonmark.node.Document;

import io.markdom.handler.MarkdomDispatcher;
import io.markdom.handler.MarkdomHandler;
import io.markdom.util.ObjectHelper;

public final class AtlassianCommonmarkDocumentMarkdomDispatcher implements MarkdomDispatcher {

	private final Document document;

	public AtlassianCommonmarkDocumentMarkdomDispatcher(Document document) throws IllegalArgumentException {
		this.document = ObjectHelper.notNull("document", document);
	}

	@Override
	public <Result> Result handle(MarkdomHandler<Result> handler) {
		document.accept(new MarkdomHandlerCommonmarkVisitor<>(ObjectHelper.notNull("handler", handler)));
		return handler.getResult();
	}

}
