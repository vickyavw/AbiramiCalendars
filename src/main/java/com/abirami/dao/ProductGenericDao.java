package com.abirami.dao;

import java.util.List;

import com.abirami.model.Product;

public interface ProductGenericDao {

	List<Product> getAll();
	
	Product get(final int id);
	
	int save(final Product product);
	
	List<Product> getListOfIds(final List<Integer> ids);
	
	//Making it generic list to support any type of values
	<T> List<Product> getAllByQueryParams(final List<String> queryParam, final List<T> value);
	
	//Making it generic to support any value in range
	<T> List<Product> getAllInRange(final String productType, final String keyQuery, final T min, final T max);
	
	List<Product> getRelatedProducts(final Product product, final int expectedCount);
	
	//<Product> void saveOrUpdate(final Product object);
}
