package io.markdom.model.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.markdom.common.MarkdomKeys;
import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomCommentBlock;
import io.markdom.model.MarkdomFactory;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Property;

public final class BasicMarkdomCommentBlock extends AbstractMarkdomBlock implements MarkdomCommentBlock {

	// @formatter:off
	private static final List<Property<MarkdomCommentBlock>> PROPERTIES = new ArrayList<>(Arrays.asList(
		new Property<>(MarkdomKeys.COMMENT, MarkdomCommentBlock::getComment)
	));
	// @formatter:on		

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
		this.comment = ObjectHelper.notNull("comment", comment);;
		return this;
	}

	@Override
	public void doHandle(MarkdomHandler<?> handler) {
		handler.onCommentBlock(comment);
	}

	@Override
	public int hashCode() {
		return ObjectHelper.hashCode(this, PROPERTIES);
	}

	@Override
	public boolean equals(Object object) {
		return ObjectHelper.equals(this, MarkdomCommentBlock.class, PROPERTIES, object);
	}

	@Override
	public String toString() {
		return ObjectHelper.toString(this, PROPERTIES);
	}

}
