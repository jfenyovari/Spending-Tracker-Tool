package com.sprintform.stt.mongodb.repositories;

import com.sprintform.stt.mongodb.entities.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

}