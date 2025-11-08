package com.banking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banking.dto.TransactionDto;
import com.banking.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping
	public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
		TransactionDto createdTransaction = transactionService.createTransaction(transactionDto);
		return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<TransactionDto>> getAllTransactions() {
		List<TransactionDto> transactions = transactionService.getAllTransaction();
		return ResponseEntity.ok(transactions);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long id) {
		TransactionDto transaction = transactionService.getTransactionById(id);
		return ResponseEntity.ok(transaction);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TransactionDto> updateTransaction(@PathVariable Long id,
			@RequestBody TransactionDto transactionDto) {
		TransactionDto updatedTransaction = transactionService.updateTransaction(id, transactionDto);
		return ResponseEntity.ok(updatedTransaction);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
		transactionService.deleteTransaction(id);
		return ResponseEntity.ok("Transaction with ID " + id + " deleted successfully.");
	}
}
