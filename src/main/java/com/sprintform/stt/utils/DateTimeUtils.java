package com.sprintform.stt.utils;

import java.time.LocalDateTime;
import java.time.Month;

public final class DateTimeUtils {

	/**
	 * Returns the LocalDateTime representing the first day of the given month.
	 *
	 * @param month the month (1 to 12)
	 * @return the LocalDateTime representing the first day of the month
	 * @throws IllegalArgumentException if the month is not in the valid range
	 */
	public static LocalDateTime getFirstDayOfMonth(int month) {
		if (month < 1 || month > 12) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		return LocalDateTime.now()
				.withDayOfMonth(1)
				.withMonth(month)
				.withHour(0)
				.withMinute(0)
				.withSecond(0)
				.withNano(0);
	}

	/**
	 * Returns the LocalDateTime representing the last day of the given month.
	 *
	 * @param month the month (1 to 12)
	 * @return the LocalDateTime representing the last day of the month
	 * @throws IllegalArgumentException if the month is not in the valid range
	 */
	public static LocalDateTime getLastDayOfMonth(int month) {
		if (month < 1 || month > 12) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		int currentYear = LocalDateTime.now().getYear();
		Month monthEnum = Month.of(month);
		int lastDayOfMonth = monthEnum.maxLength();
		return LocalDateTime.of(currentYear, month, lastDayOfMonth, 23, 59, 59);
	}
}
