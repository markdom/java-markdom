package io.markdom.model.basic;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomLineBreakContent;

public final class BasicMarkdomLineBreakContent extends AbstractMarkdomContent implements MarkdomLineBreakContent {

	private boolean hard;

	BasicMarkdomLineBreakContent(MarkdomFactory factory, Boolean hard) {
		super(factory);
		setHard(hard);
	}

	@Override
	public Boolean getHard() {
		return hard;
	}

	@Override
	public BasicMarkdomLineBreakContent setHard(Boolean hard) {
		if (null == hard) {
			throw new IllegalArgumentException("The given hard is null");
		}
		this.hard = hard;
		return this;
	}

	@Override
	public void doHandle(MarkdomHandler<?> handler) {
		handler.onLineBreakContent(hard);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [hard=" + hard + "]";
	}

}
