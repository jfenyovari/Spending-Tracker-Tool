package com.sprintform.stt.mongodb.repositories;

import com.sprintform.stt.enums.CategoryEnum;
import com.sprintform.stt.mongodb.entities.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

	List<Transaction> findByCategoryAndPaidBetweenAndUserId(CategoryEnum category, LocalDateTime from, LocalDateTime to, String userId);

	List<Transaction> findByPaidBetweenAndUserId(LocalDateTime from, LocalDateTime to, String userId);

	List<Transaction> findBySummaryContainingAndCategoryAndSumBetweenAndPaidBetweenAndUserId(String summary, CategoryEnum category, int minSum, int maxSum, LocalDateTime from, LocalDateTime to, String userId);
}