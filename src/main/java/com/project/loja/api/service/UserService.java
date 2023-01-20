package com.project.loja.api.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.loja.api.model.UserModel;
import com.project.loja.api.repositorie.UserRepositories;

public class UserService {

	private final UserRepositories userRepository;

	@Autowired
	public UserService(UserRepositories userRepository) {
		this.userRepository = userRepository;
	}

	public boolean userExist(UserModel user) {
		Optional<UserModel> res = userRepository.findByCpf(user.getCpf());
		return res.isEmpty();
	}

	public String cryptography(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
		byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));

		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest) {
			hexString.append(String.format("%02X", 0xFF & b));
		}
		password = hexString.toString();
		return password;
	}

	public boolean toComparePassword(UserModel user, String password)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
		byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));
		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest) {
			hexString.append(String.format("%02X", 0xFF & b));
		}
		password = hexString.toString();
		Integer id = user.getId();
		Optional<UserModel> data = userRepository.findById(id);
		if (data.get().getPassword().equals(password))
			return true;
		else
			return false;

	}
}
