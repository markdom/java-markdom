package io.markdom.model.basic;

import java.util.Objects;

import io.markdom.handler.MarkdomHandler;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomLineBreakContent;

public final class BasicMarkdomLineBreakContent extends AbstractMarkdomContent implements MarkdomLineBreakContent {

	private Boolean hard;

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
	public int hashCode() {
		return Objects.hash(hard);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (null == object) {
			return false;
		} else if (!(object instanceof MarkdomLineBreakContent)) {
			return false;
		}
		MarkdomLineBreakContent other = (MarkdomLineBreakContent) object;
		if (!Objects.equals(hard, other.getHard())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [hard=" + hard + "]";
	}

}
