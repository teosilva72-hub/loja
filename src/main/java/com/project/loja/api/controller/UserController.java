package com.project.loja.api.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.project.loja.api.model.UserModel;
import com.project.loja.api.repositorie.UserRepositories;
import com.project.loja.api.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserRepositories userRepository;

	@Autowired
	public UserController(UserRepositories userRepository) {
		this.userRepository = userRepository;
	}

	LocalDateTime localDate = LocalDateTime.now();

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserModel create(@RequestBody @Valid UserModel user)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		UserService service = new UserService(userRepository);

		String crypto = service.cryptography(user.getPassword());
		user.setPassword(crypto);
		boolean checked = service.userExist(user);
		if (checked)
			return userRepository.save(user);
		else
			return userRepository.findById(0)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user already registered"));
	}

	@GetMapping("{id}")
	UserModel search(@PathVariable @Valid Integer id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not exist"));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable @Valid Integer id) {
		userRepository.findById(id).map(user -> {
			userRepository.delete(user);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "user not exist"));
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	UserModel update(@PathVariable Integer id, @Valid @RequestBody UserModel user) {
		
		userRepository.findById(id).map(res -> {
			//user.setId(res.getId());
			return userRepository.save(user);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not exist"));

		return user;
	}
}
