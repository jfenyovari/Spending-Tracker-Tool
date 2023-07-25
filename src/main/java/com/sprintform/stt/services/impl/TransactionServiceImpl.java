package com.sprintform.stt.services.impl;

import com.sprintform.stt.Constants;
import com.sprintform.stt.dto.TransactionDTO;
import com.sprintform.stt.enums.CategoryEnum;
import com.sprintform.stt.exceptions.SttException;
import com.sprintform.stt.mappers.TransactionMapper;
import com.sprintform.stt.mongodb.entities.Transaction;
import com.sprintform.stt.mongodb.repositories.TransactionRepository;
import com.sprintform.stt.mongodb.repositories.UserRepository;
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

	private UserRepository userRepository;

	@Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper, UserRepository userRepository) {
		this.transactionRepository = transactionRepository;
		this.transactionMapper = transactionMapper;
		this.userRepository = userRepository;
	}

	/**
	 * Retrieves all transactions.
	 *
	 * @return a list of TransactionDTO objects representing the transactions.
	 */
	@Override
	public List<TransactionDTO> findAll() {
		List<Transaction> transactions = transactionRepository.findAll();
		return transactionMapper.mapTransactions(transactions);
	}

	/**
	 * Searches for transactions based on specified filters.
	 *
	 * @param summary  the summary to search for (partially matched).
	 * @param category the category to filter by.
	 * @param minSum   the minimum sum of the transactions.
	 * @param maxSum   the maximum sum of the transactions.
	 * @param fromDate the minimum date and time of the transactions.
	 * @param toDate   the maximum date and time of the transactions.
	 * @return a list of TransactionDTO objects matching the specified filters.
	 */
	@Override
	public List<TransactionDTO> searchByFilters(String summary, CategoryEnum category, int minSum, int maxSum, LocalDateTime fromDate, LocalDateTime toDate, String userId) {
		List<Transaction> transactions = transactionRepository.findBySummaryContainingAndCategoryAndSumBetweenAndPaidBetweenAndUserId(summary, category, minSum, maxSum, fromDate, toDate, userId);
		return transactionMapper.mapTransactions(transactions);
	}

	/**
	 * Finds a transaction by its ID.
	 *
	 * @param id the ID of the transaction to find.
	 * @return the TransactionDTO object representing the found transaction.
	 * @throws SttException if the transaction with the specified ID is not found.
	 */
	@Override
	public TransactionDTO findTransaction(String id) {
		ValidationUtils.validateField("id", id);

		Optional<Transaction> transactionOptional = transactionRepository.findById(id);
		return transactionOptional.map(transaction -> transactionMapper.map(transaction)).orElseThrow(() -> new SttException(String.format(Constants.ERROR_CANNOT_FIND_TRANSACTION, "id", id), HttpStatus.NOT_FOUND));
	}

	/**
	 * Creates a new transaction.
	 *
	 * @param transactionDTO the TransactionDTO object representing the transaction to create.
	 * @throws SttException if any of the required fields in the transactionDTO is invalid.
	 */
	@Override
	public void createTransaction(TransactionDTO transactionDTO) {
		ValidationUtils.validateField("summary", transactionDTO.getSummary());
		ValidationUtils.validateField("userId", transactionDTO.getUserId());
		ValidationUtils.validateField("currency", transactionDTO.getCurrency());
		ValidationUtils.validateField("paid", transactionDTO.getPaid().toString());

		if (userRepository.findById(transactionDTO.getUserId()).isEmpty()) {
			throw new SttException("User does not exist", HttpStatus.NOT_FOUND);
		}

		Transaction transaction = transactionMapper.map(transactionDTO);
		transactionRepository.insert(transaction);
	}

	/**
	 * Updates an existing transaction.
	 *
	 * @param transactionDTO the TransactionDTO object representing the transaction to update.
	 * @throws SttException if the transaction with the specified ID is not found.
	 * @throws SttException if any of the required fields in the transactionDTO is invalid.
	 */
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

	/**
	 * Deletes a transaction by its ID.
	 *
	 * @param id the ID of the transaction to delete.
	 * @throws SttException if the transaction with the specified ID is not found.
	 */
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
