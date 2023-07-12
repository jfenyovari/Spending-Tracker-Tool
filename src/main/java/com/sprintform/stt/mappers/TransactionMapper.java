package com.sprintform.stt.mappers;

import com.sprintform.stt.dto.TransactionDTO;
import com.sprintform.stt.mongodb.entities.Transaction;

import java.util.List;

public interface TransactionMapper {

	TransactionDTO map(Transaction transaction);

	Transaction map(TransactionDTO dto);

	List<TransactionDTO> mapTransactions(List<Transaction> transactions);

	List<Transaction> mapDtos(List<TransactionDTO> dtos);
}
