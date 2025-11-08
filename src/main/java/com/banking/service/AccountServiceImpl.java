package com.banking.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.banking.dto.AccountDto;
import com.banking.entity.Account;
import com.banking.entity.User;
import com.banking.exception.*;
import com.banking.mapper.AccountMapper;
import com.banking.repository.AccountRepository;
import com.banking.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        User user = userRepository.findById(accountDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User with ID " + accountDto.getUserId() + " not found"));

        
        if (accountRepository.existsByAccountNumber(accountDto.getAccountNumber())) {
            throw new DuplicateAccountException(
                    "Account number " + accountDto.getAccountNumber() + " already exists");
        }

        Account account = AccountMapper.mapToAccount(accountDto, user);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

   
    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(
                        "Account with ID " + id + " does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    
    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();

        if (accounts.isEmpty()) {
            throw new AccountNotFoundException("No accounts found in the system");
        }

        return accounts.stream()
                .map(AccountMapper::mapToAccountDto)
                .collect(Collectors.toList());
    }
    
    @Transactional
    @Override
    public AccountDto deposit(Long id, Double amount) {
        if (amount <= 0)
            throw new InvalidTransactionException("Deposit amount must be greater than zero");

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + id + " not found"));

        account.setBalance(account.getBalance().add(BigDecimal.valueOf(amount)));
        return AccountMapper.mapToAccountDto(accountRepository.save(account));
    }

    @Transactional
    @Override
    public AccountDto withdraw(Long id, Double amount) {
        if (amount <= 0)
            throw new InvalidTransactionException("Withdrawal amount must be greater than zero");

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + id + " not found"));

        BigDecimal withdrawAmount = BigDecimal.valueOf(amount);
        if (account.getBalance().compareTo(withdrawAmount) < 0)
            throw new InsufficientBalanceException("Insufficient balance. Available: " + account.getBalance());

        account.setBalance(account.getBalance().subtract(withdrawAmount));
        return AccountMapper.mapToAccountDto(accountRepository.save(account));
    }
   
    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(
                        "Account with ID " + id + " does not exist"));

        accountRepository.delete(account);
    }

}
