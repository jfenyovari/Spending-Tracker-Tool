package com.sprintform.stt.mappers;

import com.sprintform.stt.dto.TransactionDTO;
import com.sprintform.stt.mongodb.entities.Transaction;

public interface TransactionMapper {

	TransactionDTO map(Transaction transaction);

	Transaction map(TransactionDTO dto);
}
