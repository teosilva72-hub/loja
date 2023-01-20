package com.project.loja.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.loja.api.model.ProductModel;
import com.project.loja.api.repositorie.ProductRepositories;

public class ProductService {
	
	private final ProductRepositories productRepositories;
	@Autowired
	public ProductService(ProductRepositories productRepositories) {
		this.productRepositories = productRepositories;
	}

	public ProductModel createProduct(ProductModel product) {
		Double purchase = product.getPurchaseprice();
		Double sale = product.getSalevalue();
		Double profit = sale - purchase;
		product.setProfit(profit);
		product.set_active(true);
		return product;
	}

	public boolean productExist(String barcode) {
		Optional<ProductModel> res = productRepositories.findByBarcode(barcode);
		return res.isEmpty();
	}
}
