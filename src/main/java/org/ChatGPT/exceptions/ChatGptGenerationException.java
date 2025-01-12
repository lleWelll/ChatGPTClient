package org.ChatGPT.exceptions;

public class ChatGptGenerationException extends RuntimeException {
	public ChatGptGenerationException() {
	}

	public ChatGptGenerationException(String message) {
		super(message);
	}

	public ChatGptGenerationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChatGptGenerationException(Throwable cause) {
		super(cause);
	}

	public ChatGptGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
