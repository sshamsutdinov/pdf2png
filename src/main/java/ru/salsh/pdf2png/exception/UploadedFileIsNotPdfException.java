package ru.salsh.pdf2png.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UploadedFileIsNotPdfException extends RuntimeException {

	public UploadedFileIsNotPdfException() {
	}

	public UploadedFileIsNotPdfException(String message) {
		super(message);
	}

	public UploadedFileIsNotPdfException(String message, Throwable cause) {
		super(message, cause);
	}

	public UploadedFileIsNotPdfException(Throwable cause) {
		super(cause);
	}

	public UploadedFileIsNotPdfException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
