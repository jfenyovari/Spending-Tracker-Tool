package com.sprintform.stt.services;

import com.sprintform.stt.enums.CategoryEnum;

import java.util.Map;

public interface StatisticService {
	long predictCostsForNextMonth(int monthsConsidered, String userId);

	long predictRemainingCostsForCurrentMonth(int monthsConsidered, String userId);

	Map<CategoryEnum, Integer> collectCostsByCategory(int monthsConsidered, String userId);

	Map<CategoryEnum, Integer> predictCostsForNextMonthByCategory(int monthsConsidered, String userId);
}
