package com.sprintform.stt.services.impl;

import com.sprintform.stt.dto.TransactionDTO;
import com.sprintform.stt.mappers.TransactionMapper;
import com.sprintform.stt.mongodb.entities.Transaction;
import com.sprintform.stt.mongodb.repositories.TransactionRepository;
import com.sprintform.stt.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

	public List<TransactionDTO> getTransactions() {
		List<Transaction> all = transactionRepository.findAll();
		List<TransactionDTO> result = new ArrayList<>();
		all.forEach(t -> result.add(transactionMapper.map(t)));
		return result;
	}

	@Override
	public TransactionDTO getTransaction(String id) {
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
