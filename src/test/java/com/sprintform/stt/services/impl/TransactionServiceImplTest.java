package com.sprintform.stt.services.impl;

import com.sprintform.stt.dto.TransactionDTO;
import com.sprintform.stt.enums.CategoryEnum;
import com.sprintform.stt.exceptions.SttException;
import com.sprintform.stt.mappers.TransactionMapper;
import com.sprintform.stt.mongodb.entities.Transaction;
import com.sprintform.stt.mongodb.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private TransactionMapper transactionMapper;

	@InjectMocks
	private TransactionServiceImpl transactionService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAll() {
		// Prepare mock data
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction());
		transactions.add(new Transaction());

		List<TransactionDTO> transactionDTOs = new ArrayList<>();
		transactionDTOs.add(new TransactionDTO());
		transactionDTOs.add(new TransactionDTO());

		// Mock behavior
		when(transactionRepository.findAll()).thenReturn(transactions);
		when(transactionMapper.mapTransactions(transactions)).thenReturn(transactionDTOs);

		// Perform the method call
		List<TransactionDTO> result = transactionService.findAll();

		// Verify the results
		verify(transactionRepository, times(1)).findAll();
		verify(transactionMapper, times(1)).mapTransactions(transactions);
		Assertions.assertEquals(transactionDTOs, result);
	}

	@Test
	void testSearchByFilters() {
		// Prepare mock data
		String summary = "Test summary";
		CategoryEnum category = CategoryEnum.FOOD;
		int minSum = 100;
		int maxSum = 200;
		LocalDateTime fromDate = LocalDateTime.now();
		LocalDateTime toDate = LocalDateTime.now();
		String userId ="id";

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction());
		transactions.add(new Transaction());

		List<TransactionDTO> transactionDTOs = new ArrayList<>();
		transactionDTOs.add(new TransactionDTO());
		transactionDTOs.add(new TransactionDTO());

		// Mock behavior
		when(transactionRepository.findBySummaryContainingAndCategoryAndSumBetweenAndPaidBetweenAndUserId(summary, category, minSum, maxSum, fromDate, toDate, userId)).thenReturn(transactions);
		when(transactionMapper.mapTransactions(transactions)).thenReturn(transactionDTOs);

		// Perform the method call
		List<TransactionDTO> result = transactionService.searchByFilters(summary, category, minSum, maxSum, fromDate, toDate, userId);

		// Verify the results
		verify(transactionRepository, times(1)).findBySummaryContainingAndCategoryAndSumBetweenAndPaidBetweenAndUserId(summary, category, minSum, maxSum, fromDate, toDate, userId);
		verify(transactionMapper, times(1)).mapTransactions(transactions);
		Assertions.assertEquals(transactionDTOs, result);
	}

	@Test
	void testFindTransaction() {
		// Prepare mock data
		String id = "test-id";
		Transaction transaction = new Transaction();
		TransactionDTO transactionDTO = new TransactionDTO();

		// Mock behavior
		when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));
		when(transactionMapper.map(transaction)).thenReturn(transactionDTO);

		// Perform the method call
		TransactionDTO result = transactionService.findTransaction(id);

		// Verify the results
		verify(transactionRepository, times(1)).findById(id);
		verify(transactionMapper, times(1)).map(transaction);
		Assertions.assertEquals(transactionDTO, result);
	}

	@Test
	void testFindTransactionNotFound() {
		// Prepare mock data
		String id = "test-id";

		// Mock behavior
		when(transactionRepository.findById(id)).thenReturn(Optional.empty());

		// Perform the method call and verify the exception
		Assertions.assertThrows(SttException.class, () -> transactionService.findTransaction(id));

		// Verify the results
		verify(transactionRepository, times(1)).findById(id);
	}

	@Test
	void testCreateTransaction() {
		// Prepare mock data
		TransactionDTO transactionDTO = new TransactionDTO();
		Transaction transaction = new Transaction();
		transactionDTO.setSummary("summary");
		transactionDTO.setCurrency("HUF");
		transactionDTO.setPaid(LocalDateTime.now());

		// Mock behavior
		when(transactionMapper.map(transactionDTO)).thenReturn(transaction);

		// Perform the method call
		transactionService.createTransaction(transactionDTO);

		// Verify the results
		verify(transactionMapper, times(1)).map(transactionDTO);
		verify(transactionRepository, times(1)).insert(transaction);
	}

	@Test
	void testUpdateTransaction() {
		// Prepare mock data
		String id = "test-id";
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setId(id);
		transactionDTO.setSummary("summary");
		transactionDTO.setCurrency("HUF");
		transactionDTO.setPaid(LocalDateTime.now());
		Transaction transaction = new Transaction();

		// Mock behavior
		when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));
		when(transactionMapper.map(transactionDTO)).thenReturn(transaction);

		// Perform the method call
		transactionService.updateTransaction(transactionDTO);

		// Verify the results
		verify(transactionRepository, times(1)).findById(id);
		verify(transactionMapper, times(1)).map(transactionDTO);
		verify(transactionRepository, times(1)).save(transaction);
	}

	@Test
	void testUpdateTransactionNotFound() {
		// Prepare mock data
		String id = "test-id";
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setId(id);
		transactionDTO.setSummary("summary");
		transactionDTO.setCurrency("HUF");
		transactionDTO.setPaid(LocalDateTime.now());

		// Mock behavior
		when(transactionRepository.findById(id)).thenReturn(Optional.empty());

		// Perform the method call and verify the exception
		Assertions.assertThrows(SttException.class, () -> transactionService.updateTransaction(transactionDTO));

		// Verify the results
		verify(transactionRepository, times(1)).findById(id);
	}

	@Test
	void testDeleteTransaction() {
		// Prepare mock data
		String id = "test-id";
		Transaction transaction = new Transaction();

		// Mock behavior
		when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));

		// Perform the method call
		transactionService.deleteTransaction(id);

		// Verify the results
		verify(transactionRepository, times(1)).findById(id);
		verify(transactionRepository, times(1)).deleteById(id);
	}

	@Test
	void testDeleteTransactionNotFound() {
		// Prepare mock data
		String id = "test-id";

		// Mock behavior
		when(transactionRepository.findById(id)).thenReturn(Optional.empty());

		// Perform the method call and verify the exception
		Assertions.assertThrows(SttException.class, () -> transactionService.deleteTransaction(id));

		// Verify the results
		verify(transactionRepository, times(1)).findById(id);
	}
}