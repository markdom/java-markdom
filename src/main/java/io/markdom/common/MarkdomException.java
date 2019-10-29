package io.markdom.common;

/**
 * A {@link MarkdomException} indicates that a Markdom related operation failed.
 */
public class MarkdomException extends RuntimeException {

	private static final long serialVersionUID = -7598753346017119204L;

	/**
	 * Creates a new {@link MarkdomException} with the given message and cause.
	 *
	 * @param message The message.
	 * @param cause   The cause of this {@link MarkdomException}.
	 */
	public MarkdomException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new {@link MarkdomException} with the given message.
	 *
	 * @param message The message.
	 */
	public MarkdomException(String message) {
		super(message);
	}

	/**
	 * Creates a new {@link MarkdomException} with the given cause.
	 *
	 * @param cause The cause of this {@link MarkdomException}.
	 */
	public MarkdomException(Throwable cause) {
		super(null == cause ? null : cause.getMessage(), cause);
	}

}