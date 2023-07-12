package com.sprintform.stt.services;

import com.sprintform.stt.enums.CategoryEnum;

import java.util.Map;

public interface StatisticService {
	long predictCostsForNextMonth(int monthsConsidered);

	long predictRemainingCostsForCurrentMonth(int monthsConsidered);

	Map<CategoryEnum, Integer> collectCostsByCategory(int monthsConsidered);

	Map<CategoryEnum, Integer> predictCostsForNextMonthByCategory(int monthsConsidered);
}
