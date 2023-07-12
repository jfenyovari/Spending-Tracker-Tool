package com.sprintform.stt.controllers;

import com.sprintform.stt.dto.ErrorInfo;
import com.sprintform.stt.exceptions.SttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

	@ExceptionHandler(value = {SttException.class})
	protected ResponseEntity<Object> handleConflict(SttException ex, ServletWebRequest request) {
		logger.error("Error on service level", ex);
		return handleExceptionInternal(ex, new ErrorInfo(request.getRequest().getRequestURI(), ex.getMessage()), HttpHeaders.EMPTY, ex.getHttpStatus(), request);
	}
}