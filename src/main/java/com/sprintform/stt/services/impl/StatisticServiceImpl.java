package com.sprintform.stt.services.impl;

import com.sprintform.stt.enums.CategoryEnum;
import com.sprintform.stt.mongodb.entities.Transaction;
import com.sprintform.stt.mongodb.repositories.TransactionRepository;
import com.sprintform.stt.services.StatisticService;
import com.sprintform.stt.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImpl implements StatisticService {

	private TransactionRepository transactionRepository;

	@Autowired
	public StatisticServiceImpl(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	/**
	 * Predicts the costs for the next month based on the given number of months considered.
	 *
	 * @param monthsConsidered The number of previous months to consider for prediction.
	 * @return The predicted costs for the next month.
	 */
	@Override
	public long predictCostsForNextMonth(int monthsConsidered, String userId) {
		int currentMonth = LocalDateTime.now().getMonthValue();
		List<Transaction> previousTransactions = transactionRepository.findByPaidBetweenAndUserId(DateTimeUtils.getFirstDayOfMonth(currentMonth - monthsConsidered), DateTimeUtils.getLastDayOfMonth(currentMonth - 1), userId);

		long sum = previousTransactions.stream()
				.mapToLong(Transaction::getSum)
				.sum();

		return sum / monthsConsidered;
	}

	/**
	 * Predicts the remaining costs for the current month based on the given number of months considered.
	 *
	 * @param monthsConsidered The number of previous months to consider for prediction.
	 * @return The predicted remaining costs for the current month.
	 */
	@Override
	public long predictRemainingCostsForCurrentMonth(int monthsConsidered, String userId) {
		int currentMonth = LocalDateTime.now().getMonthValue();
		List<Transaction> previousTransactions = transactionRepository.findByPaidBetweenAndUserId(DateTimeUtils.getFirstDayOfMonth(currentMonth - monthsConsidered), DateTimeUtils.getLastDayOfMonth(currentMonth - 1), userId);

		long previousPredictionSum = previousTransactions.stream()
				.mapToLong(Transaction::getSum)
				.sum();

		List<Transaction> currentMonthTransactions = transactionRepository.findByPaidBetweenAndUserId(DateTimeUtils.getFirstDayOfMonth(currentMonth), LocalDateTime.now(), userId);
		long currentMonthSum = currentMonthTransactions.stream()
				.mapToLong(Transaction::getSum)
				.sum();
		return previousPredictionSum / monthsConsidered - currentMonthSum;
	}

	/**
	 * Collects the costs by category for the given number of months considered.
	 *
	 * @param monthsConsidered The number of previous months to consider for collecting costs.
	 * @return A map of category to total cost for the specified months.
	 */
	@Override
	public Map<CategoryEnum, Integer> collectCostsByCategory(int monthsConsidered, String userId) {
		int currentMonth = LocalDateTime.now().getMonthValue();
		List<Transaction> transactions = transactionRepository.findByPaidBetweenAndUserId(DateTimeUtils.getFirstDayOfMonth(currentMonth - monthsConsidered), DateTimeUtils.getLastDayOfMonth(currentMonth - 1), userId);
		return transactions.stream()
				.collect(Collectors.toMap(
						Transaction::getCategory,
						Transaction::getSum,
						Integer::sum
				));
	}

	/**
	 * Predicts the costs for the next month by category based on the given number of months considered.
	 *
	 * @param monthsConsidered The number of previous months to consider for prediction.
	 * @return A map of category to predicted cost for the next month.
	 */
	@Override
	public Map<CategoryEnum, Integer> predictCostsForNextMonthByCategory(int monthsConsidered, String userId) {
		Map<CategoryEnum, Integer> costsByCategoryMap = collectCostsByCategory(monthsConsidered, userId);
		costsByCategoryMap.replaceAll((key, value) -> value / monthsConsidered);
		return costsByCategoryMap;
	}
}
