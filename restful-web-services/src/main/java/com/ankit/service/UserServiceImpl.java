package com.ankit.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ankit.model.User;
import com.ankit.repository.UserRepository;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public User updateUser(User user, Long id) {
		if (userRepository.findById(id).isPresent()) {
			user.setId(id);
			user = userRepository.save(user);
		}
		return user;
	}

	@Override
	public User saveUser(@Valid User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteUserById(Long id) {
		userRepository.deleteUserById(id);
	}

	public List<User> getUserByNameContaining(String searchString) {
		return userRepository.findByUserNameContaining(searchString);
	}

	public List<User> getUserByNameLike(String searchString) {
		return userRepository.findByUserNameLike(searchString);
	}

}