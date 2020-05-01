package com.ankit.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ankit.exception.ResourceNotFoundException;
import com.ankit.model.User;
import com.ankit.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	MessageSource messageSource;

	@GetMapping
	public ResponseEntity<List<User>> getUsers() {
		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public User getUserByID(@PathVariable Long id) {
		User user = userService.getUserById(id);
		if (user == null) {
			throw new ResourceNotFoundException("User not found with Id" + id);
		}

		// HATEAOS

		return user;
	}

	@PutMapping("/{id}")
	public User updateUsers(@Valid @RequestBody User user, @PathVariable Long id) {
		return userService.updateUser(user, id);
	}

	@PostMapping
	public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {

		User savedUser = userService.saveUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUserById(id);
	}

	// Internationalize Locale
	@GetMapping("/locale")
	public String getLocaleMessage() {
		return messageSource.getMessage("goodmorning.message", null, LocaleContextHolder.getLocale());
	}

	@GetMapping("/search1/{searchString}")
	public ResponseEntity<List<User>> getUserByNameContaining(@PathVariable("searchString") final String searchString) {
		List<User> userList = userService.getUserByNameContaining(searchString);
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	@GetMapping("/search2/{searchString}")
	public ResponseEntity<List<User>> getUserByNameLike(@PathVariable("searchString") final String searchString) {
		List<User> userList = userService.getUserByNameLike(searchString);
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

}
