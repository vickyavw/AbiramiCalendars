package com.abirami.dao;

import java.util.List;

import com.abirami.model.Product;
import com.abirami.model.ProductsApiResponse;

public interface ProductGenericDao {

	ProductsApiResponse getAll(final String sortBy, 
			final String sortDirection, 
			final Integer pageSize,
			final Integer pageNumber);
	
	Product get(final int id);
	
	int save(final Product product);
	
	List<Product> getListOfIds(final List<Integer> ids);
	
	//Making it generic list to support any type of values
	<T> ProductsApiResponse getAllByQueryParams(final List<String> queryParam, 
			final List<T> value,
			final String sortBy, 
			final String sortDirection, 
			final Integer pageSize,
			final Integer pageNumber);
	
	//Making it generic to support any value in range
	<T> ProductsApiResponse getAllInRange(final String productType, 
			final String keyQuery, 
			final T min, 
			final T max,
			final String sortBy, 
			final String sortDirection, 
			final Integer pageSize,
			final Integer pageNumber);
	
	List<Product> getRelatedProducts(final Product product, 
			final int expectedCount);
	
	//<Product> void saveOrUpdate(final Product object);
}
