package ru.salsh.pdf2png.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ConvertionFailedException extends RuntimeException {

	public ConvertionFailedException() {
	}

	public ConvertionFailedException(String message) {
		super(message);
	}

	public ConvertionFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConvertionFailedException(Throwable cause) {
		super(cause);
	}

	public ConvertionFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
