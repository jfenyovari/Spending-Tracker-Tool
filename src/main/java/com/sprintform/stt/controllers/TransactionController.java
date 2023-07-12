package com.sprintform.stt.controllers;


import com.sprintform.stt.dto.TransactionDTO;
import com.sprintform.stt.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/list")
	public ResponseEntity<List<TransactionDTO>> getTransactions() {
		return ResponseEntity.ok(transactionService.getTransactions());
	}

	@GetMapping()
	public ResponseEntity<TransactionDTO> getTransaction(@RequestParam String id) {
		return ResponseEntity.ok(transactionService.getTransaction(id));
	}

	@PostMapping()
	public ResponseEntity<String> createTransaction(@RequestBody TransactionDTO transaction) {
		transactionService.createTransaction(transaction);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping()
	public ResponseEntity<String> updateTransaction(@RequestBody TransactionDTO transaction) {
		transactionService.updateTransaction(transaction);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping()
	public ResponseEntity<String> deleteTransaction(@RequestParam String id) {
		transactionService.deleteTransaction(id);
		return ResponseEntity.noContent().build();
	}
}
