package com.sprintform.stt.controllers;


import com.sprintform.stt.dto.TransactionDTO;
import com.sprintform.stt.dto.requests.TransactionSearchRequest;
import com.sprintform.stt.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Transaction", description = "Transaction management APIs")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Operation(
			summary = "Retrieve all the transactions",
			description = "Get a list of transactions object by specifying its id. " +
					"The response is an Array with transactions. Requires admins.",
			tags = {"transaction", "get"})
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TransactionDTO.class), mediaType = "application/json")}),
			@ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
			@ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
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
				searchRequest.getTo(),
				searchRequest.getUserId()));
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
