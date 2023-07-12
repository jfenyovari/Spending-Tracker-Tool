package com.sprintform.stt.utils;

import com.sprintform.stt.Constants;
import com.sprintform.stt.exceptions.SttException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

public final class ValidationUtils {

	public static void validateField(String fieldName, String value) {
		if (StringUtils.isBlank(value))
			throw new SttException(String.format(Constants.ERROR_GIVEN_FIELD_IS_INVALID, fieldName, value), HttpStatus.BAD_REQUEST);
	}
}
