package com.banking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.banking.entity.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

	private Long id;

	private BigDecimal amount;

	private TransactionType transactionType;

	private String description;

	private Long accountId;

	private LocalDateTime createdAt;

}
