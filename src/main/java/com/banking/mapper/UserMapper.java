package com.banking.mapper;

import java.util.ArrayList;
import java.util.List;

import com.banking.dto.AccountDto;
import com.banking.dto.UserDto;
import com.banking.entity.Account;
import com.banking.entity.User;

public class UserMapper {
    // DTO → Entity 
    public static User mapToUser(UserDto userDto) {
    	
        List<Account> accounts = new ArrayList<>();
        if (userDto.getAccounts() != null) {
            for (AccountDto accountDto : userDto.getAccounts()) {
                Account account = AccountMapper.mapToAccount(accountDto, null);
                account.setUser(null); //set later in service
                accounts.add(account);
            }
        }

        return new User(
                userDto.getId(),
                userDto.getUserName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getPhone(),
                accounts,
                userDto.getRole(),
                userDto.getCreatedAt()
        );
    }

    // Entity → DTO 
    public static UserDto mapToUserDto(User user) {
        List<AccountDto> accountDtos = new ArrayList<>();
        if (user.getAccounts() != null) {
            for (Account account : user.getAccounts()) {
                accountDtos.add(AccountMapper.mapToAccountDto(account));
            }
        }

        return new UserDto(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                accountDtos,
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
