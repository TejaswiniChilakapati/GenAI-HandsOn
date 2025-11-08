package com.banking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entity.Account;


public interface AccountRepository extends JpaRepository<Account,Long> {

	boolean existsByAccountNumber(String accountNumber);

	Optional<Account> findByAccountNumber(String fromAccount);

}
