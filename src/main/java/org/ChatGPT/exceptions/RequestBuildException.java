package org.ChatGPT.exceptions;

public class RequestBuildException extends RuntimeException {
	public RequestBuildException() {
	}

	public RequestBuildException(String message) {
		super(message);
	}

	public RequestBuildException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestBuildException(Throwable cause) {
		super(cause);
	}

	public RequestBuildException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
