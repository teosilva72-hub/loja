package com.project.loja.api.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Data
@ToString
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 150)
	@NotEmpty(message = "{campo.name.require}")
	private String name;
	
	@Column(nullable = false, length = 11)
	@NotNull(message = "{campo.cpf.require}")
	@CPF(message = "{campo.cpf.invalid}")
	private String cpf;
	
	@Column(nullable = false, length = 150)
	@NotNull(message = "{campo.email.require}")
	@Email
	private String email;

	@Column(nullable = false, length = 150)
	@NotNull(message = "{campo.password.require}")
	private String password;
	
	@Column(nullable = false, length = 300)
	private String image;
	
	private UUID uuid;
	
	@Column(updatable = false)
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dt_register;
	
	@PrePersist
	public void prePersist() {
		setDt_register(LocalDateTime.now());
	}
	
	

}
