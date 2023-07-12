package com.sprintform.stt.services;

import com.sprintform.stt.dto.TransactionDTO;

import java.util.List;


public interface TransactionService {

	List<TransactionDTO> getTransactions();

	TransactionDTO getTransaction(String id);

	void createTransaction(TransactionDTO transactionDTO);

	void updateTransaction(TransactionDTO transactionDTO);

	void deleteTransaction(String id);
}
