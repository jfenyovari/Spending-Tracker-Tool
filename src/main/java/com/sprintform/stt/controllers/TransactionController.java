package com.sprintform.stt.controllers;


import com.sprintform.stt.dto.requests.TransactionSearchRequest;
import com.sprintform.stt.dto.TransactionDTO;
import com.sprintform.stt.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/list")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<TransactionDTO>> getTransactions() {
		return ResponseEntity.ok(transactionService.findAll());
	}

	@GetMapping("/search")
	public ResponseEntity<List<TransactionDTO>> findTransactions(@RequestBody TransactionSearchRequest searchRequest) {
		return ResponseEntity.ok(transactionService.searchByFilters(
				searchRequest.getSummary(),
				searchRequest.getCategory(),
				searchRequest.getMinSum(),
				searchRequest.getMaxSum(),
				searchRequest.getFrom(),
				searchRequest.getTo()));
	}

	@GetMapping()
	public ResponseEntity<TransactionDTO> getTransaction(@RequestParam String id) {
		return ResponseEntity.ok(transactionService.findTransaction(id));
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
