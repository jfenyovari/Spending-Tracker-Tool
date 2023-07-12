package com.sprintform.stt.services.impl;

import com.sprintform.stt.dto.TransactionDTO;
import com.sprintform.stt.enums.CategoryEnum;
import com.sprintform.stt.mappers.TransactionMapper;
import com.sprintform.stt.mongodb.entities.Transaction;
import com.sprintform.stt.mongodb.repositories.TransactionRepository;
import com.sprintform.stt.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

	private TransactionRepository transactionRepository;

	private TransactionMapper transactionMapper;

	@Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
		this.transactionRepository = transactionRepository;
		this.transactionMapper = transactionMapper;
	}

	@Override
	public List<TransactionDTO> findAll() {
		List<Transaction> transactions = transactionRepository.findAll();
		return transactionMapper.mapTransactions(transactions);
	}

	@Override
	public List<TransactionDTO> searchByFilters(String summary, CategoryEnum category, int minSum, int maxSum, LocalDateTime fromDate, LocalDateTime toDate) {
		List<Transaction> transactions = transactionRepository.findBySummaryContainingAndCategoryAndSumBetweenAndPaidBetween(summary, category, minSum, maxSum, fromDate, toDate);
		return transactionMapper.mapTransactions(transactions);
	}

	@Override
	public TransactionDTO findTransaction(String id) {
		Optional<Transaction> transactionOptional = transactionRepository.findById(id);
		return transactionOptional.map(transaction -> transactionMapper.map(transaction)).orElse(null);
	}

	@Override
	public void createTransaction(TransactionDTO transactionDTO) {
		Transaction transaction = transactionMapper.map(transactionDTO);
		transactionRepository.insert(transaction);
	}

	@Override
	public void updateTransaction(TransactionDTO transactionDTO) {
		Optional<Transaction> byId = transactionRepository.findById(transactionDTO.getId());
		if (byId.isPresent()) {
			transactionRepository.save(transactionMapper.map(transactionDTO));
		}
	}

	@Override
	public void deleteTransaction(String id) {
		Optional<Transaction> byId = transactionRepository.findById(id);
		if (byId.isPresent()) {
			transactionRepository.deleteById(id);
		}
	}
}
