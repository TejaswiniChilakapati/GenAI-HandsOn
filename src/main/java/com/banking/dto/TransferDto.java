package com.banking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.banking.entity.TransferStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {

	private Long id;

	private String fromAccount;

	private String toAccount;

	private BigDecimal amount;

	private String description;

	private TransferStatus status;

	private LocalDateTime createdAt;

}
