package com.java.react.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.react.domain.User;
import com.java.react.exception.UsernameAlreadyExistsException;
import com.java.react.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User saveUser(User newUser) {
		try {
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

			// Username has to be unique (exception)

			// Make sure that password and confirmPassword match
			// We don't persist or show the confirmPassword
			newUser.setConfirmPassword("");
			return userRepository.save(newUser);
		} catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
		}
	}

}