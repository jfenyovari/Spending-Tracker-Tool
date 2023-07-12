package com.sprintform.stt.mappers.impl;

import com.sprintform.stt.dto.TransactionDTO;
import com.sprintform.stt.mappers.TransactionMapper;
import com.sprintform.stt.mongodb.entities.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperImpl implements TransactionMapper {
	@Override
	public TransactionDTO map(Transaction transaction) {
		if (transaction == null) {
			return null;
		}

		TransactionDTO dto = new TransactionDTO();
		dto.setId(transaction.getId());
		dto.setCategory(transaction.getCategory());
		dto.setPaid(transaction.getPaid());
		dto.setSummary(transaction.getSummary());
		dto.setSum(transaction.getSum());
		dto.setCurrency(transaction.getCurrency());
		return dto;
	}

	@Override
	public Transaction map(TransactionDTO dto) {
		if (dto == null) {
			return null;
		}

		Transaction transaction = new Transaction();
		transaction.setId(dto.getId());
		transaction.setCategory(dto.getCategory());
		transaction.setPaid(dto.getPaid());
		transaction.setSummary(dto.getSummary());
		transaction.setSum(dto.getSum());
		transaction.setCurrency(dto.getCurrency());
		return transaction;
	}
}