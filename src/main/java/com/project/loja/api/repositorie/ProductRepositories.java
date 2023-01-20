package com.project.loja.api.repositorie;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.loja.api.model.ProductModel;

public interface ProductRepositories extends JpaRepository<ProductModel, Integer>{

	Optional<ProductModel> findByBarcode(String barcode);

}
