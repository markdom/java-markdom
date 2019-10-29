package io.markdom.model.basic;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomCommentBlock;
import io.markdom.model.MarkdomFactory;

public final class BasicMarkdomCommentBlock extends AbstractMarkdomBlock implements MarkdomCommentBlock {

	private String comment;

	BasicMarkdomCommentBlock(MarkdomFactory factory, String comment) {
		super(factory);
		setComment(comment);
	}

	@Override
	public String getComment() {
		return comment;
	}

	public BasicMarkdomCommentBlock setComment(String comment) {
		if (null == comment) {
			throw new IllegalArgumentException("The given comment is null");
		}
		this.comment = comment;
		return this;
	}

	@Override
	public void doHandle(MarkdomHandler<?> handler) {
		handler.onCommentBlock(comment);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [comment=" + comment + "]";
	}

}
