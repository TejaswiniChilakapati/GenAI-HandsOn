package com.banking.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id ;
	
	@Column(nullable=false ,precision = 15)
	private BigDecimal amount;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
    private TransactionType transactionType;//(DEPOSIT, WITHDRAWAL, TRANSFER)
    
    @Column(nullable=false)
	private String description;
	
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
	
	@Column(name="created_at",nullable=false)
	private LocalDateTime createdAt;
}
