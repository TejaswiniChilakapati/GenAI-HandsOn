package com.banking.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
	
	private int statusCode;         // HTTP status code
    private String message;         // Error message
    private String errorCode;       // For clarity: e.g., "BANKING_ERROR" or "Internal_ERROR"
    private LocalDateTime timestamp;
}
