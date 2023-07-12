package com.sprintform.stt.services.impl;

import com.sprintform.stt.enums.CategoryEnum;
import com.sprintform.stt.mongodb.entities.Transaction;
import com.sprintform.stt.mongodb.repositories.TransactionRepository;
import com.sprintform.stt.utils.DateTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

class StatisticServiceImplTest {

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private StatisticServiceImpl statisticService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testPredictCostsForNextMonth() {
		// Prepare mock data
		int monthsConsidered = 3;
		int currentMonth = LocalDateTime.now().getMonthValue();
		LocalDateTime fromDate = DateTimeUtils.getFirstDayOfMonth(currentMonth - monthsConsidered);
		LocalDateTime toDate = DateTimeUtils.getLastDayOfMonth(currentMonth - 1);

		List<Transaction> previousTransactions = new ArrayList<>();
		previousTransactions.add(createTransaction(100));
		previousTransactions.add(createTransaction(200));
		previousTransactions.add(createTransaction(300));

		// Mock behavior
		when(transactionRepository.findByPaidBetween(fromDate, toDate)).thenReturn(previousTransactions);

		// Perform the method call
		long result = statisticService.predictCostsForNextMonth(monthsConsidered);

		// Verify the results
		verify(transactionRepository, times(1)).findByPaidBetween(fromDate, toDate);
		long sum = previousTransactions.stream().mapToLong(Transaction::getSum).sum();
		long expected = sum / monthsConsidered;
		Assertions.assertEquals(expected, result);
	}

	@Test
	void testCollectCostsByCategory() {
		// Prepare mock data
		int monthsConsidered = 3;
		int currentMonth = LocalDateTime.now().getMonthValue();
		LocalDateTime fromDate = DateTimeUtils.getFirstDayOfMonth(currentMonth - monthsConsidered);
		LocalDateTime toDate = DateTimeUtils.getLastDayOfMonth(currentMonth - 1);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(createTransaction(CategoryEnum.FINANCIAL, 100));
		transactions.add(createTransaction(CategoryEnum.FINANCIAL, 200));
		transactions.add(createTransaction(CategoryEnum.FOOD, 300));

		// Mock behavior
		when(transactionRepository.findByPaidBetween(fromDate, toDate)).thenReturn(transactions);

		// Perform the method call
		Map<CategoryEnum, Integer> result = statisticService.collectCostsByCategory(monthsConsidered);

		// Verify the results
		verify(transactionRepository, times(1)).findByPaidBetween(fromDate, toDate);
		Map<CategoryEnum, Integer> expected = transactions.stream()
				.collect(Collectors.toMap(Transaction::getCategory, Transaction::getSum, Integer::sum));
		Assertions.assertEquals(expected, result);
	}

	// Helper method to create a transaction with the given category and sum
	private Transaction createTransaction(CategoryEnum category, int sum) {
		Transaction transaction = new Transaction();
		transaction.setCategory(category);
		transaction.setSum(sum);
		return transaction;
	}

	// Helper method to create a transaction with the given sum (default category: CATEGORY_A)
	private Transaction createTransaction(int sum) {
		return createTransaction(CategoryEnum.FINANCIAL, sum);
	}
}