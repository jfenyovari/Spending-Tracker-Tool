package com.sprintform.stt.controllers;

import com.sprintform.stt.enums.CategoryEnum;
import com.sprintform.stt.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/statistic")
public class StatisticController {

	private StatisticService statisticService;

	@Autowired
	public StatisticController(StatisticService statisticService) {
		this.statisticService = statisticService;
	}

	@GetMapping("/predictCostsForNextMonth")
	public ResponseEntity<Long> predictCostsForNextMonth(@RequestParam(defaultValue = "3") int monthConsidered) {
		Long result = statisticService.predictCostsForNextMonth(monthConsidered);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/predictRemainingCostsForCurrentMonth")
	public ResponseEntity<Long> predictRemainingCostsForCurrentMonth(@RequestParam(defaultValue = "3") int monthConsidered) {
		Long result = statisticService.predictRemainingCostsForCurrentMonth(monthConsidered);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/collectCostsByCategory")
	public ResponseEntity<Map<CategoryEnum, Integer>> collectCostsByCategory(@RequestParam(defaultValue = "3") int monthConsidered) {
		Map<CategoryEnum, Integer> categoryEnumIntegerMap = statisticService.collectCostsByCategory(monthConsidered);
		return ResponseEntity.ok(categoryEnumIntegerMap);
	}

	@GetMapping("/predictCostsForNextMonthByCategory")
	public ResponseEntity<Map<CategoryEnum, Integer>> predictCostsForNextMonthByCategory(@RequestParam(defaultValue = "3") int monthConsidered) {
		Map<CategoryEnum, Integer> costsByCategoryMap = statisticService.predictCostsForNextMonthByCategory(monthConsidered);
		return ResponseEntity.ok(costsByCategoryMap);
	}
}
