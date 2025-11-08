package com.banking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.banking.entity.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long id;

    private String accountNumber;

    private BigDecimal balance;

    private AccountType accountType;
    private Long userId;  

    private LocalDateTime createdAt;
}
