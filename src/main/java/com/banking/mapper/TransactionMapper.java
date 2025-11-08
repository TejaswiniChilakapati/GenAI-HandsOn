package com.banking.mapper;

import com.banking.dto.TransactionDto;
import com.banking.entity.Account;
import com.banking.entity.Transaction;

public class TransactionMapper {
	
	
	
	public static Transaction mapToTransaction(TransactionDto transactionDto, Account account) {
		
		
		Transaction transaction =new Transaction(
				
				transactionDto.getId(),
				transactionDto.getAmount(),
				transactionDto.getTransactionType(),
				transactionDto.getDescription(),
				account,
				transactionDto.getCreatedAt());
		return transaction;
	}

	public static TransactionDto mapToTransactionDto(Transaction transaction) {
		
		
		TransactionDto transactionDto= new TransactionDto(
				
				transaction.getId(),
				transaction.getAmount(),
				transaction.getTransactionType(),
				transaction.getDescription(),
				transaction.getAccount() != null ? transaction.getAccount().getId() : null,
				transaction.getCreatedAt());
		return transactionDto;
				
				
	}
}
