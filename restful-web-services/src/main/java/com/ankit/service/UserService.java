package com.ankit.service;

import java.util.List;

import javax.validation.Valid;

import com.ankit.model.User;

public interface UserService {

	List<User> getAllUsers();

	User updateUser(@Valid User user, Long id);

	User saveUser(@Valid User user);

	User getUserById(Long id);

	void deleteUserById(Long id);

	List<User> getUserByNameContaining(String searchString);

	List<User> getUserByNameLike(String searchString);

}