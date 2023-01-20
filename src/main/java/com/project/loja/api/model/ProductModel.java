package com.project.loja.api.model;

import java.time.LocalDateTime;

import org.aspectj.weaver.ast.Not;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
@ToString
@Builder
@Table(name = "product")
public class ProductModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 100)
	@NotNull(message = "{product.barcode.require}")
	@NotEmpty(message = "{product.barcode.empty}")
	private String barcode;
	
	@Column(nullable = false, length = 150 )
	@NotNull(message = "{product.name.require}")
	private String name;
	
	@Column(nullable = false, length = 500)
	@NotNull(message = "{product.description.required}")
	private String description;
	
	@Column(nullable = false, length = 100)
	@NotNull(message = "{product.brand.require}")
	private String brand;
	
	@Column(nullable = false, length = 150)
	@NotNull(message = "{product.model.require}")
	private String model;
	
	@Column(nullable = false, length = 200)
	@NotNull(message = "{product.provider.require}")
	private String provider;
	
	@Column(nullable = false, length = 10)
	@NotNull(message = "{product.purchaseprice.required}")
	private Double purchaseprice;
	
	@Column(nullable = false, length = 10)
	@NotNull(message = "{product.salesvalue.required}")
	private Double salevalue;
	
	private Double profit;	
	private boolean is_active;
	private Integer units;
	private String image;
	
	@Column(updatable = false)
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime date_Register;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime date_Update;
	
	@PrePersist
	public void prePersist() {
		setDate_Register(LocalDateTime.now());
		setDate_Update(LocalDateTime.now());
	}
	

}
