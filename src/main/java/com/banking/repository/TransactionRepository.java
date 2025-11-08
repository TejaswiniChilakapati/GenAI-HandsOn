package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long>{

}
