package com.sprintform.stt.services.impl;

import com.sprintform.stt.Constants;
import com.sprintform.stt.dto.TransactionDTO;
import com.sprintform.stt.enums.CategoryEnum;
import com.sprintform.stt.exceptions.SttException;
import com.sprintform.stt.mappers.TransactionMapper;
import com.sprintform.stt.mongodb.entities.Transaction;
import com.sprintform.stt.mongodb.repositories.TransactionRepository;
import com.sprintform.stt.services.TransactionService;
import com.sprintform.stt.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
		ValidationUtils.validateField("id", id);

		Optional<Transaction> transactionOptional = transactionRepository.findById(id);
		return transactionOptional.map(transaction -> transactionMapper.map(transaction)).orElseThrow(() -> new SttException(String.format(Constants.ERROR_CANNOT_FIND_TRANSACTION, "id", id), HttpStatus.NOT_FOUND));
	}

	@Override
	public void createTransaction(TransactionDTO transactionDTO) {
		ValidationUtils.validateField("summary", transactionDTO.getSummary());
		ValidationUtils.validateField("currency", transactionDTO.getCurrency());
		ValidationUtils.validateField("paid", transactionDTO.getPaid().toString());
		Transaction transaction = transactionMapper.map(transactionDTO);
		transactionRepository.insert(transaction);
	}

	@Override
	public void updateTransaction(TransactionDTO transactionDTO) {
		String id = transactionDTO.getId();
		ValidationUtils.validateField("id", id);
		ValidationUtils.validateField("summary", transactionDTO.getSummary());
		ValidationUtils.validateField("currency", transactionDTO.getCurrency());
		ValidationUtils.validateField("paid", transactionDTO.getPaid().toString());
		Optional<Transaction> byId = transactionRepository.findById(id);
		if (byId.isPresent()) {
			transactionRepository.save(transactionMapper.map(transactionDTO));
		} else {
			throw new SttException(String.format(Constants.ERROR_CANNOT_FIND_TRANSACTION, "id", id), HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteTransaction(String id) {
		Optional<Transaction> byId = transactionRepository.findById(id);
		if (byId.isPresent()) {
			transactionRepository.deleteById(id);
		} else {
			throw new SttException(String.format(Constants.ERROR_CANNOT_FIND_TRANSACTION, "id", id), HttpStatus.NOT_FOUND);
		}
	}
}
