package com.banking.service;

import com.banking.dto.UserDto;
import com.banking.entity.User;
import com.banking.exception.UserNotFoundException;
import com.banking.exception.DuplicateUserException;
import com.banking.mapper.UserMapper;
import com.banking.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		if (userRepository.existsByEmail(userDto.getEmail())) {
			throw new DuplicateUserException("Email already exists: " + userDto.getEmail());
		}

		if (userRepository.existsByUserName(userDto.getUserName())) {
			throw new DuplicateUserException("Username already exists: " + userDto.getUserName());
		}

		User user = UserMapper.mapToUser(userDto);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setCreatedAt(LocalDateTime.now());

		User savedUser = userRepository.save(user);
		return UserMapper.mapToUserDto(savedUser);
	}

	@Override
	public UserDto getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
		return UserMapper.mapToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();

		if (users.isEmpty()) {
			throw new UserNotFoundException("No users found in the system");
		}

		return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
	}

	@Override
	public UserDto updateUser(Long id, UserDto userDto) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setUserName(userDto.getUserName());
		user.setPhone(userDto.getPhone());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setRole(userDto.getRole());

		User updatedUser = userRepository.save(user);
		return UserMapper.mapToUserDto(updatedUser);
	}

	@Override
	public void deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

		userRepository.delete(user);
	}
}
