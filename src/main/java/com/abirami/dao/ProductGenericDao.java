package com.abirami.dao;

import java.util.List;
import java.util.Map;

import com.abirami.model.PaginatedProductsApiResponse;
import com.abirami.model.Product;
import com.abirami.model.ProductDTO;

public interface ProductGenericDao {

	PaginatedProductsApiResponse getAll(final String sortBy, 
			final String sortDirection, 
			final Integer pageSize,
			final Integer pageNumber);
	
	ProductDTO get(final int id);
	
	int save(final Product product);
	
	List<Product> getListOfIds(final List<Integer> ids);
	
	//Making it generic Object to support any type of values
	PaginatedProductsApiResponse getAllByQueryParams(final String productType, final Map<String, Map<String, Object>> queryParamsMap,
			final String sortBy, 
			final String sortDirection, 
			final Integer pageSize,
			final Integer pageNumber);
	
	List<ProductDTO> getRelatedProducts(final ProductDTO product, 
			final int expectedCount);
	
	//<Product> void saveOrUpdate(final Product object);
}
