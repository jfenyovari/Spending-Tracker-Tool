package com.sprintform.stt.mongodb.changesets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.sprintform.stt.mongodb.entities.Transaction;
import com.sprintform.stt.mongodb.repositories.TransactionRepository;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@ChangeLog
public class DatabaseChangeLog {

	@ChangeSet(order = "001", id = "addTransactions", author = "Jakab")
	public void addTransactions(TransactionRepository transactionRepository) throws IOException {

		File resource = new ClassPathResource("transactions.json").getFile();
		String transactionsJson = new String(Files.readAllBytes(resource.toPath()));

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		List<Transaction> transactions = mapper.readValue(transactionsJson, new TypeReference<>() {
		});

		transactionRepository.insert(transactions);
	}

}
