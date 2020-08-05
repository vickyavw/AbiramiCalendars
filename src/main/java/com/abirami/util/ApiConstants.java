package com.abirami.util;

public interface ApiConstants {

	String GET_ALL_PRODUCTS_API_URL = "http://localhost:8080/api/products";
	String GET_PRODUCT_BY_ID_API_URL = "http://localhost:8080/api/products/{productId}";
	String GET_PRODUCTS_BY_CATEGORY_API_URL = "http://localhost:8080/api/products/category/{categoryId}";
	String GET_PRODUCTS_BY_PRICE_API_URL = "http://localhost:8080/api/products/price";
	String GET_RELATED_PRODUCTS_OF_ID_URL = "http://localhost:8080/api/products/related/{productId}";
	
	String CATEGORIES_API_URL = "http://localhost:8080/api/categories";
	
	//Query Params for APIs
	String PRICE_MIN_QUERY_PARAM = "priceMin";
	String PRICE_MAX_QUERY_PARAM = "priceMax";
	String PRODUCT_TYPE_QUERY_PARAM = "productType";
	String ROWS_TO_FETCH = "expectedCount";

	String PRODUCT_ID_REPLACE = "{productId}";
	String CATEGORY_ID_REPLACE = "{categoryId}";
}
