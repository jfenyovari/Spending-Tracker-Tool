package com.sprintform.stt.mongodb.repositories;

import com.sprintform.stt.enums.CategoryEnum;
import com.sprintform.stt.mongodb.entities.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

	List<Transaction> findByCategoryAndPaidBetween(CategoryEnum category, LocalDateTime from, LocalDateTime to);

	List<Transaction> findByPaidBetween(LocalDateTime from, LocalDateTime to);

	List<Transaction> findBySummaryContainingAndCategoryAndSumBetweenAndPaidBetween(String summary, CategoryEnum category, int minSum, int maxSum, LocalDateTime from, LocalDateTime to);
}