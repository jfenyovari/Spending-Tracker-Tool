package com.sprintform.stt.mongodb.changesets;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.sprintform.stt.mongodb.entities.Transaction;
import com.sprintform.stt.mongodb.repositories.TransactionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class DatabaseChangeLog {

	@ChangeSet(order = "001", id = "addTransactions", author = "Jakab")
	public void seedDatabase(TransactionRepository transactionRepository) {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction("10", "summary", "category", 123, "HUF", LocalDateTime.now()));

		transactionRepository.insert(transactions);
	}

}
