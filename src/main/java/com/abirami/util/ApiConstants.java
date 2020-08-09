package com.abirami.util;

import java.util.HashMap;
import java.util.Map;

public class ApiConstants {

	public static final String GET_ALL_PRODUCTS_API_URL = "http://localhost:8080/api/products";
	public static final String GET_PRODUCT_BY_ID_API_URL = "http://localhost:8080/api/products/{productId}";
	public static final String GET_PRODUCTS_BY_QUERY_API_URL = "http://localhost:8080/api/products/query";
	public static final String GET_RELATED_PRODUCTS_OF_ID_URL = "http://localhost:8080/api/products/related/{productId}";
	
	public static final String CATEGORIES_API_URL = "http://localhost:8080/api/categories";
	
	public static final String FORMATS_API_URL = "http://localhost:8080/api/formats";
	
	//Query Params for APIs
	public static final String PRICE_MIN_QUERY_PARAM = "priceMin";
	public static final String PRICE_MAX_QUERY_PARAM = "priceMax";
	public static final String PRODUCT_TYPE_QUERY_PARAM = "productType";
	public static final String CATEGORY_ID_QUERY_PARAM = "categoryId";
	public static final String CATEGORY_NAME_QUERY_PARAM = "categoryName";
	public static final String FORMAT_ID_QUERY_PARAM = "formatId";
	public static final String FORMAT_NAME_QUERY_PARAM = "formatName";
	public static final String ROWS_TO_FETCH = "expectedCount";
	public static final String API_QUERY_PARAMS = "queryParams";
	public static final String SORT_BY_QUERY_PARAM = "sortBy";
	public static final String SORT_DIRECTION_QUERY_PARAM = "sortDirection";
	public static final String PAGE_SIZE_QUERY_PARAM = "pageSize";
	public static final String PAGE_NUMBER_QUERY_PARAM = "pageNumber";
	public static final String TOTAL_PAGES_QUERY_PARAM = "noOfPages";

	public static final String PRODUCT_ID_REPLACE = "{productId}";
	public static final String CATEGORY_ID_REPLACE = "{categoryId}";
	public static final int DEFAULT_PAGE_NUMBER = 1;
	public static final int DEFAULT_PAGE_SIZE = 50;
	
	public static final Map<String, String> ALLOWED_EXACT_MATCH_QUERY_PARAMS_TO_CRITERIA;
	public static final Map<String, String> ALLOWED_RANGE_QUERY_PARAMS_TO_CRITERIA;
	public static final Map<String, String> ALLOWED_PARTIAL_MATCH_QUERY_PARAMS_TO_CRITERIA;
	public static final Map<String, String> ALLOWED_IN_QUERY_PARAMS_TO_CRITERIA;
	
	// Instantiating the static map 
    static
    { 
    	ALLOWED_EXACT_MATCH_QUERY_PARAMS_TO_CRITERIA = new HashMap<>(); 
    	ALLOWED_EXACT_MATCH_QUERY_PARAMS_TO_CRITERIA.put(CATEGORY_ID_QUERY_PARAM, "category.categoryId"); 
    	ALLOWED_EXACT_MATCH_QUERY_PARAMS_TO_CRITERIA.put(CATEGORY_NAME_QUERY_PARAM, "category.categoryName"); 
    	ALLOWED_EXACT_MATCH_QUERY_PARAMS_TO_CRITERIA.put(FORMAT_ID_QUERY_PARAM, "format.formatId"); 
    	ALLOWED_EXACT_MATCH_QUERY_PARAMS_TO_CRITERIA.put(FORMAT_NAME_QUERY_PARAM, "format.formatName"); 
    	
    	ALLOWED_RANGE_QUERY_PARAMS_TO_CRITERIA = new HashMap<>();
    	ALLOWED_RANGE_QUERY_PARAMS_TO_CRITERIA.put(PRICE_MIN_QUERY_PARAM, "price");
    	ALLOWED_RANGE_QUERY_PARAMS_TO_CRITERIA.put(PRICE_MAX_QUERY_PARAM, "price");
    	
    	ALLOWED_PARTIAL_MATCH_QUERY_PARAMS_TO_CRITERIA = new HashMap<>();
    	
    	ALLOWED_IN_QUERY_PARAMS_TO_CRITERIA = new HashMap<>();
    } 
	
}