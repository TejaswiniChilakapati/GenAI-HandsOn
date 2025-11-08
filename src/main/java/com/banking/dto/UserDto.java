package com.banking.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.banking.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private Long id;
	private String userName;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String phone;
	private List<AccountDto> accounts;
    private Role role;
	private LocalDateTime createdAt;

}
