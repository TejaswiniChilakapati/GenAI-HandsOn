package com.banking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.banking.dto.TransactionDto;
import com.banking.entity.Account;
import com.banking.entity.Transaction;
import com.banking.exception.AccountNotFoundException;
import com.banking.exception.InvalidTransactionException;
import com.banking.exception.TransactionNotFoundException;
import com.banking.mapper.TransactionMapper;
import com.banking.repository.AccountRepository;
import com.banking.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
	private final AccountRepository accountRepository;

	public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
		this.transactionRepository = transactionRepository;
		this.accountRepository = accountRepository;
	}

	@Override
	public TransactionDto createTransaction(TransactionDto transactionDto) {
		if (transactionDto.getAmount() == null || transactionDto.getAmount().doubleValue() <= 0) {
			throw new InvalidTransactionException("Transaction amount must be greater than zero");
		}

		Account account = accountRepository.findById(transactionDto.getAccountId()).orElseThrow(
				() -> new AccountNotFoundException("Account with ID " + transactionDto.getAccountId() + " not found"));

		Transaction transaction = TransactionMapper.mapToTransaction(transactionDto, account);
		transaction.setCreatedAt(LocalDateTime.now());

		Transaction savedTransaction = transactionRepository.save(transaction);
		return TransactionMapper.mapToTransactionDto(savedTransaction);
	}

	@Override
	public TransactionDto getTransactionById(Long id) {
		Transaction transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new TransactionNotFoundException("Transaction with ID " + id + " not found"));
		return TransactionMapper.mapToTransactionDto(transaction);
	}

	public List<TransactionDto> getAllTransaction() {
		List<Transaction> transactions = transactionRepository.findAll();

		if (transactions.isEmpty()) {
			throw new TransactionNotFoundException("No transactions found in the system");
		}

		return transactions.stream().map(TransactionMapper::mapToTransactionDto).collect(Collectors.toList());
	}

	@Override
	public TransactionDto updateTransaction(Long id, TransactionDto transactionDto) {
		Transaction transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new TransactionNotFoundException("Transaction with ID " + id + " not found"));

		if (transactionDto.getAmount() == null || transactionDto.getAmount().doubleValue() <= 0) {
			throw new InvalidTransactionException("Transaction amount must be greater than zero");
		}

		transaction.setDescription(transactionDto.getDescription());
		transaction.setTransactionType(transactionDto.getTransactionType());
		transaction.setAmount(transactionDto.getAmount());

		Transaction updatedTransaction = transactionRepository.save(transaction);
		return TransactionMapper.mapToTransactionDto(updatedTransaction);
	}

	@Override
	public void deleteTransaction(Long id) {
		Transaction transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new TransactionNotFoundException("Transaction with ID " + id + " not found"));

		transactionRepository.delete(transaction);
	}
}
