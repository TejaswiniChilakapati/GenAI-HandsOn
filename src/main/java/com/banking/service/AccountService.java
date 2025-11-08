package com.banking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.banking.dto.AccountDto;
@Service
public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    List<AccountDto> getAllAccounts();

    void deleteAccount(Long id);

	AccountDto deposit(Long id, Double amount);

	AccountDto withdraw(Long id, Double amount);
}
