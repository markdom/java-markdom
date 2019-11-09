package io.markdom.common;

public class MarkdomException extends RuntimeException {

	private static final long serialVersionUID = -7598753346017119204L;

	public MarkdomException(String message, Throwable cause) {
		super(message, cause);
	}

	public MarkdomException(String message) {
		super(message);
	}

	public MarkdomException(Throwable cause) {
		super(null == cause ? null : cause.getMessage(), cause);
	}

}