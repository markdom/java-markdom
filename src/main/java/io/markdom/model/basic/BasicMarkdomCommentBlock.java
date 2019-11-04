package io.markdom.model.basic;

import java.util.Objects;

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
	public int hashCode() {
		return Objects.hash(comment);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (null == object) {
			return false;
		} else if (!(object instanceof MarkdomCommentBlock)) {
			return false;
		}
		MarkdomCommentBlock other = (MarkdomCommentBlock) object;
		if (!Objects.equals(comment, other.getComment())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [comment=" + comment + "]";
	}

}
