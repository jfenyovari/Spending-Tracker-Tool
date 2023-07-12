package com.sprintform.stt.services;

import com.sprintform.stt.dto.TransactionDTO;
import com.sprintform.stt.enums.CategoryEnum;

import java.time.LocalDateTime;
import java.util.List;


public interface TransactionService {

	List<TransactionDTO> findAll();

	List<TransactionDTO> searchByFilters(String summary, CategoryEnum category, int minSum, int maxSum, LocalDateTime fromDate, LocalDateTime toDate);

	TransactionDTO findTransaction(String id);

	void createTransaction(TransactionDTO transactionDTO);

	void updateTransaction(TransactionDTO transactionDTO);

	void deleteTransaction(String id);
}
