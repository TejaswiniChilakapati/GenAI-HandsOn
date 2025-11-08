package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entity.Transfer;

public interface TransferRepository extends JpaRepository<Transfer,Long>{

}
