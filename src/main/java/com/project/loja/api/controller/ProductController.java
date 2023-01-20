package com.project.loja.api.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import com.project.loja.api.model.ProductModel;
import com.project.loja.api.repositorie.ProductRepositories;
import com.project.loja.api.service.ProductService;

import jakarta.validation.Valid;
import lombok.experimental.Delegate;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	private final ProductRepositories productRepositories;

	@Autowired
	public ProductController(ProductRepositories productRepositories) {
		this.productRepositories = productRepositories;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	ProductModel create(@RequestBody @Valid ProductModel product) {
		ProductService service = new ProductService(productRepositories);
		boolean checked = service.productExist(product.getBarcode());
		if (checked) {

			ProductModel res = service.createProduct(product);
			return productRepositories.save(res);
		} else
			return productRepositories.findById(0)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product already registered"));
	}

	@GetMapping("{id}")
	ProductModel search(@Valid @PathVariable Integer id) {
		return productRepositories.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not exist"));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable @Valid Integer id) {
		productRepositories.findById(id).map(user -> {
			productRepositories.delete(user);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	ProductModel update(@PathVariable Integer id, @Valid @RequestBody ProductModel product) {
		product.setDate_Update(LocalDateTime.now());
		productRepositories.findById(id).map(res -> {
			product.setId(res.getId());
			return productRepositories.save(product);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not exist"));
		return product;
	}
}
