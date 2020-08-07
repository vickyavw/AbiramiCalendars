package com.abirami.api.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.abirami.api.ProductResource;
import com.abirami.dao.CategoryGenericDao;
import com.abirami.dao.ProductGenericDao;
import com.abirami.dao.impl.CategoryGenericDaoImpl;
import com.abirami.dao.impl.ProductGenericDaoImpl;
import com.abirami.model.ApiError;
import com.abirami.model.Product;
import com.abirami.model.ProductsApiResponse;
import com.abirami.util.ApiConstants;
import com.abirami.util.ApiValidator;
import com.abirami.util.ProductUtils;

/**
 * @author vicky
 *
 */

@Path("products")
@Consumes({"application/json"})
@Produces({"application/json"})
public class ProductResourceImpl implements ProductResource {

	@Override
	public Response getProducts(String productType, String sortBy, String sortDirection, Integer pageSize, Integer pageNumber) {
		ProductGenericDao productDao = new ProductGenericDaoImpl();
		ProductsApiResponse response = new ProductsApiResponse();
		try {
			if(!ApiValidator.isValidSortAttr(sortBy, sortDirection))
				return ProductUtils.setApiBadRequestError(1001);
			if(StringUtils.isNotBlank(productType)) {
				//Filter by product type - calendars/diaries/labels etc
				//Sending list of queryParams and list of paramValues
				response = productDao.getAllByQueryParams(productType, new HashMap<String, Map<String,Object>>(), sortBy, sortDirection, pageSize, pageNumber);
			}else {
				//get all products
				response = productDao.getAll(sortBy, sortDirection, pageSize, pageNumber);
			}
			return Response.status(HttpStatus.SC_OK).entity(response).build();
		}
		catch (Exception e) {
			return ProductUtils.setApiServerError();
		}
	}

	@Override
	public Response getProduct(int productId) {
		if(productId == 0) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(1001);
			apiError.setErrorDescription("Product Id mandatory" );
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(apiError).build();
		}
		ProductGenericDao productDao = new ProductGenericDaoImpl();
		try {
			Product product = productDao.get(productId);
			return Response.status(HttpStatus.SC_OK).entity(product).build();
		}
		catch (Exception e) {
			return ProductUtils.setApiServerError();
		}
	}

	@Override
	public Response addProduct(Product product) {
		if(null == product || null == product.getCategoryName()) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(1001);
			apiError.setErrorDescription("Product mandatory" );
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(apiError).build();
		}
		CategoryGenericDao categoryDao = new CategoryGenericDaoImpl();
		ProductGenericDao productDao = new ProductGenericDaoImpl();
		try {
			//get Category by categoryId and set in the product before saving the product.
			product.setCategory(categoryDao.get(Integer.valueOf(product.getCategoryName())));
			int id = productDao.save(product);
			product.setProductId(id);
			//setting the correct product name in the response
			product.setCategoryName(product.getCategory().getDisplayName());
			return Response.status(HttpStatus.SC_OK).entity(product).build();
		}
		catch (Exception e) {
			return ProductUtils.setApiServerError();
		}
	}

	@Override
	public Response updateProduct(int productId, Product product) {
		// TODO update db
		return null;
	}

	@Override
	public Response deleteProduct(int productId) {
		// TODO update db
		return null;
	}

	@Override
	public Response getProductsByCriteria(String productType, String queryParams, String sortBy, String sortDirection, Integer pageSize, Integer pageNumber) {
		
		ProductGenericDao productDao = new ProductGenericDaoImpl();
		ProductsApiResponse response = new ProductsApiResponse();
		try {
			Map<String, Map<String, Object>> queryParamsMap = new HashMap<String, Map<String,Object>>(); 
			populateQueryParamsMap(queryParams, queryParamsMap);
			response = productDao.getAllByQueryParams(productType, queryParamsMap, sortBy, sortDirection, pageSize, pageNumber);
			
			return Response.status(HttpStatus.SC_OK).entity(response).build();
		}
		catch (Exception e) {
			return ProductUtils.setApiServerError();
		}
	}

	@Override
	public Response getProductWithRelatedProductsInfo(int productId, int expectedCount) {
		if(productId == 0) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(1001);
			apiError.setErrorDescription("Product Id mandatory" );
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(apiError).build();
		}
		ProductGenericDao productDao = new ProductGenericDaoImpl();
		List<Product> responseProducts = new ArrayList<Product>();
		try {
			Product product = productDao.get(productId);
			responseProducts.add(product);
			List<Product> products = productDao.getRelatedProducts(product, expectedCount);
			responseProducts.addAll(products);
			return Response.status(HttpStatus.SC_OK).entity(responseProducts).build();
		}
		catch (Exception e) {
			return ProductUtils.setApiServerError();
		}
	}

	//Add all the queryParam mapping here
	private void populateQueryParamsMap(String queryParams, Map<String, Map<String, Object>> queryParamsMap) {
		
		if (StringUtils.isNotEmpty(queryParams)) {
			try {
				queryParams = URLDecoder.decode(queryParams, StandardCharsets.UTF_8.toString());
		        String[] parameters = queryParams.split("&");
		        for (String parameter : parameters) {
		            String[] keyValuePair = parameter.split("=");
		            /*
		             * Adding only category and price filter for now
		             * To add more filters when needed
		             * Map of map Setup examples. Map will contain following keys with value examples
		             * {exactMatch={category.categoryId=1}}
		             * partialMatch - {displayName, test}, {}, {}
		             * range - {price, {1,100}}, {}
		             * in - {displayName, {'test', 'a', 'b', .....}}
		             */
		            if(null != ApiConstants.ALLOWED_EXACT_MATCH_QUERY_PARAMS_TO_CRITERIA.get(keyValuePair[0])) {
		            	Map<String, Object> values = queryParamsMap.get("exactMatch");
		            	if(null == values)
		            		values = new HashMap<String, Object>();
		            	if(StringUtils.isNumeric(keyValuePair[1]))
		            		values.put(ApiConstants.ALLOWED_EXACT_MATCH_QUERY_PARAMS_TO_CRITERIA.get(keyValuePair[0]), Integer.parseInt(keyValuePair[1]));
		            	else
		            		values.put(ApiConstants.ALLOWED_EXACT_MATCH_QUERY_PARAMS_TO_CRITERIA.get(keyValuePair[0]), keyValuePair[1]);
		            	queryParamsMap.put("exactMatch", values);
		            }
		            else if(null != ApiConstants.ALLOWED_RANGE_QUERY_PARAMS_TO_CRITERIA.get(keyValuePair[0])) {
		            	Map<String, Object> values = queryParamsMap.get("range");
		            	List<BigDecimal> priceMinMax = null;
		            	
		            	if(null == values) {
		            		values = new HashMap<String, Object>();
		            		priceMinMax = new ArrayList<BigDecimal>();
		            	}
		            	else if (values.get(ApiConstants.ALLOWED_RANGE_QUERY_PARAMS_TO_CRITERIA.get(keyValuePair[0])) == null) {
		            		priceMinMax = new ArrayList<BigDecimal>();
		            	}
		            	else {
		            		priceMinMax = (List<BigDecimal>) values.get(ApiConstants.ALLOWED_RANGE_QUERY_PARAMS_TO_CRITERIA.get(keyValuePair[0]));
		            	}
		            	
		            	priceMinMax.add(new BigDecimal(keyValuePair[1]));
		            	values.put(ApiConstants.ALLOWED_RANGE_QUERY_PARAMS_TO_CRITERIA.get(keyValuePair[0]), priceMinMax);
		            	queryParamsMap.put("range", values);
		            }
		            else if(null != ApiConstants.ALLOWED_PARTIAL_MATCH_QUERY_PARAMS_TO_CRITERIA.get(keyValuePair[0])) {
		            	Map<String, Object> values = queryParamsMap.get("partialMatch");
		            	if(null == values)
		            		values = new HashMap<String, Object>();
		            	values.put(ApiConstants.ALLOWED_PARTIAL_MATCH_QUERY_PARAMS_TO_CRITERIA.get(keyValuePair[0]), keyValuePair[1]);
		            	queryParamsMap.put("partialMatch", values);
		            }
		            else if(null != ApiConstants.ALLOWED_IN_QUERY_PARAMS_TO_CRITERIA.get(keyValuePair[0])) {
		            	Map<String, Object> values = queryParamsMap.get("in");
		            	List<Object> priceMinMax = null;
		            	
		            	if(null == values) {
		            		values = new HashMap<String, Object>();
		            	}
		            	else if (values.get(ApiConstants.ALLOWED_IN_QUERY_PARAMS_TO_CRITERIA.get(keyValuePair[0])) == null) {
		            		priceMinMax = new ArrayList<>();
		            	}
		            	else {
		            		priceMinMax = (List<Object>) values.get(ApiConstants.ALLOWED_RANGE_QUERY_PARAMS_TO_CRITERIA.get(keyValuePair[0]));
		            	}
		            	
		            	priceMinMax.add(new BigDecimal(keyValuePair[1]));
		            	values.put(ApiConstants.ALLOWED_RANGE_QUERY_PARAMS_TO_CRITERIA.get(keyValuePair[0]), priceMinMax);
		            	queryParamsMap.put("in", values);
		            }
		        }
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

}
