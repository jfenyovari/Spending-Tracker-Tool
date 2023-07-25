package com.sprintform.stt.utils;

import com.sprintform.stt.services.models.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

	public static String getCurrentUserId() {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return String.valueOf(userDetails.getId());
	}
}
