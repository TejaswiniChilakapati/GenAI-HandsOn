package com.banking.mapper;

import com.banking.dto.AccountDto;
import com.banking.entity.Account;
import com.banking.entity.User;

public class AccountMapper {

    // DTO → Entity
    public static Account mapToAccount(AccountDto accountDto, User user) {
        Account account = new Account(
                accountDto.getId(),
                accountDto.getAccountNumber(),
                accountDto.getBalance(),
                accountDto.getAccountType(),
                user, // user entity passed from service
                null,
                accountDto.getCreatedAt()
        );
        return account;
    }

    // Entity → DTO
    public static AccountDto mapToAccountDto(Account account) {
        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getAccountType(),
                account.getUser() != null ? account.getUser().getId():null,		
                account.getCreatedAt()
        );
        return accountDto;
    }
}
