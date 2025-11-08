package com.banking.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.dto.TransferDto;
import com.banking.entity.Account;
import com.banking.entity.Transaction;
import com.banking.entity.TransactionType;
import com.banking.entity.Transfer;
import com.banking.entity.TransferStatus;
import com.banking.exception.AccountNotFoundException;
import com.banking.exception.InsufficientBalanceException;
import com.banking.exception.InvalidTransactionException;
import com.banking.exception.SameAccountTransferException;
import com.banking.exception.TransactionNotFoundException;
import com.banking.exception.TransferFailedException;
import com.banking.mapper.TransferMapper;
import com.banking.repository.AccountRepository;
import com.banking.repository.TransactionRepository;
import com.banking.repository.TransferRepository;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransferServiceImpl(TransferRepository transferRepository, AccountRepository accountRepository,TransactionRepository transactionRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository=transactionRepository;
    }
    @Override
    public TransferDto createTransfer(TransferDto transferDto) {

        // ✅ Basic validation
        if (transferDto.getFromAccount() == null || transferDto.getFromAccount().isBlank()
                || transferDto.getToAccount() == null || transferDto.getToAccount().isBlank()) {
            throw new InvalidTransactionException("From and To account numbers cannot be null or blank");
        }

        if (transferDto.getAmount() == null || transferDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException("Transfer amount must be greater than zero");
        }

        if (transferDto.getFromAccount().equals(transferDto.getToAccount())) {
            throw new SameAccountTransferException("Sender and receiver accounts cannot be the same");
        }

        // ✅ Fetch accounts
        Account fromAccount = accountRepository.findByAccountNumber(transferDto.getFromAccount())
                .orElseThrow(() -> new AccountNotFoundException("Sender account not found"));

        Account toAccount = accountRepository.findByAccountNumber(transferDto.getToAccount())
                .orElseThrow(() -> new AccountNotFoundException("Receiver account not found"));

        // ✅ Balance check
        if (fromAccount.getBalance().compareTo(transferDto.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance in sender account");
        }

        try {
            BigDecimal amount = transferDto.getAmount();

            // ✅ Update balances
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            // ✅ Save Transfer record
            Transfer transfer = TransferMapper.mapToTransfer(transferDto);
            transfer.setStatus(TransferStatus.COMPLETED);
            transfer.setCreatedAt(LocalDateTime.now());
            Transfer savedTransfer = transferRepository.save(transfer);

            // ✅ Automatically record transaction history

            // Sender’s transaction
            Transaction senderTransaction = new Transaction();
            senderTransaction.setAccount(fromAccount);
            senderTransaction.setAmount(amount);
            senderTransaction.setTransactionType(TransactionType.TRANSFER);
            senderTransaction.setDescription("Transferred to account " + toAccount.getAccountNumber());
            senderTransaction.setCreatedAt(LocalDateTime.now());
            transactionRepository.save(senderTransaction);

            // Receiver’s transaction
            Transaction receiverTransaction = new Transaction();
            receiverTransaction.setAccount(toAccount);
            receiverTransaction.setAmount(amount);
            receiverTransaction.setTransactionType(TransactionType.TRANSFER);
            receiverTransaction.setDescription("Received from account " + fromAccount.getAccountNumber());
            receiverTransaction.setCreatedAt(LocalDateTime.now());
            transactionRepository.save(receiverTransaction);

            // ✅ Return DTO
            return TransferMapper.mapToTransferDto(savedTransfer);

        } catch (Exception e) {
            throw new TransferFailedException("Transfer failed: " + e.getMessage());
        }
    }

  

   /* @Override
    public TransferDto createTransfer(TransferDto transferDto) {

        // Validate basic fields
        if (transferDto.getFromAccount() == null || transferDto.getFromAccount().isBlank()
                || transferDto.getToAccount() == null || transferDto.getToAccount().isBlank()) {
            throw new InvalidTransactionException("From and To account numbers cannot be null or blank");
        }

        if (transferDto.getAmount() == null || transferDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException("Transfer amount must be greater than zero");
        }

        if (transferDto.getFromAccount().equals(transferDto.getToAccount())) {
            throw new SameAccountTransferException("Sender and receiver accounts cannot be the same");
        }

        // Fetch accounts from database
        Account fromAccount = accountRepository.findByAccountNumber(transferDto.getFromAccount())
                .orElseThrow(() -> new AccountNotFoundException("Sender account not found"));

        Account toAccount = accountRepository.findByAccountNumber(transferDto.getToAccount())
                .orElseThrow(() -> new AccountNotFoundException("Receiver account not found"));

        // Check balance
        if (fromAccount.getBalance().compareTo(transferDto.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance in sender account");
        }

        try {
            BigDecimal amount = transferDto.getAmount();

            // Deduct and credit
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            // Record transfer
            Transfer transfer = TransferMapper.mapToTransfer(transferDto);
            transfer.setStatus(TransferStatus.COMPLETED);

            Transfer savedTransfer = transferRepository.save(transfer);
            return TransferMapper.mapToTransferDto(savedTransfer);

        } catch (Exception e) {
            throw new TransferFailedException("Transfer failed: " + e.getMessage());
        }
    }*/
    

    @Override
    public TransferDto getTransferById(Long id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transfer not found with ID: " + id));
        return TransferMapper.mapToTransferDto(transfer);
    }

    @Override
    public List<TransferDto> getAllTransfers() {
        List<Transfer> transfers = transferRepository.findAll();

        if (transfers.isEmpty()) {
            throw new TransactionNotFoundException("No transfers found");
        }

        return transfers.stream()
                .map(TransferMapper::mapToTransferDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTransfer(Long id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transfer not found with ID: " + id));
        transferRepository.delete(transfer);
    }
}
