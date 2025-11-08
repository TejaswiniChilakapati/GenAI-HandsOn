package com.banking.service;

import java.util.List;

import com.banking.dto.TransactionDto;

public interface TransactionService {
	
	TransactionDto createTransaction(TransactionDto transactionDto);

    TransactionDto getTransactionById(Long id);

    List<TransactionDto> getAllTransaction();

    TransactionDto updateTransaction(Long id, TransactionDto transactionDto);

    void deleteTransaction(Long id);

}
