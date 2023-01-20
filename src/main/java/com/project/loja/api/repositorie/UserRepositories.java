package com.project.loja.api.repositorie;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.loja.api.model.UserModel;

public interface UserRepositories extends JpaRepository<UserModel, Integer>{
	Optional<UserModel> findByCpf(String cpf);

	Optional<UserModel> findByEmail(String email);
}
