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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="transfers")
public class Transfer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; //(Primary Key)
	
	@Column(name = "from_account", nullable = false)
	private String fromAccount; //(Account Number)
	
	@Column(name = "to_account", nullable = false)
	private String toAccount;  //(Account Number)
	
	@Column(nullable=false ,precision = 15, scale = 2)
	private BigDecimal amount; 
	
	@Column(length=250)
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private TransferStatus status;
	
	@Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Auto-set timestamp before saving
    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
