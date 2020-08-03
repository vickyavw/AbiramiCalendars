package com.abirami.dao;

import java.util.List;

import com.abirami.model.Product;

public interface ProductDao {

	List<Product> getProducts();
	
	Product getProduct(int productId);
	
	int addProduct(Product product);
}
