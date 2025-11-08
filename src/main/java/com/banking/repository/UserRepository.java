package com.banking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByUserName(String userName);

	boolean existsByEmail(String email);
	
	Optional<User> findByUserName(String userName);
	
	Optional<User> findByUserNameOrEmail(String userName, String email);

}
