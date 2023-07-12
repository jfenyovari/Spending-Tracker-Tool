package com.sprintform.stt.exceptions;

import org.springframework.http.HttpStatus;

public class SttException extends RuntimeException {

	private HttpStatus httpStatus;

	public SttException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
