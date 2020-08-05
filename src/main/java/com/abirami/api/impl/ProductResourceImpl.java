package com.abirami.api.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;

import com.abirami.api.ProductResource;
import com.abirami.dao.CategoryGenericDao;
import com.abirami.dao.ProductGenericDao;
import com.abirami.dao.impl.CategoryGenericDaoImpl;
import com.abirami.dao.impl.ProductGenericDaoImpl;
import com.abirami.model.ApiError;
import com.abirami.model.Product;
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
	public Response getProducts(String productType) {
		ProductGenericDao productDao = new ProductGenericDaoImpl();
		List<Product> products = new ArrayList<Product>();
		try {
			if(StringUtils.isNotBlank(productType)) {
				//Filter by product type - calendars/diaries/labels etc
				//Sending list of queryParams and list of paramValues
				products = productDao.getAllByQueryParams(Arrays.asList("productType"), Arrays.asList(productType));
			}else {
				//get all products
				products = productDao.getAll();
			}
			return Response.status(HttpStatus.SC_OK).entity(products).build();
		}
		catch (Exception e) {
			return ProductUtils.setApiError();
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
			product.setCategoryName(product.getCategory().getDisplayName());
			return Response.status(HttpStatus.SC_OK).entity(product).build();
		}
		catch (Exception e) {
			return ProductUtils.setApiError();
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
			return ProductUtils.setApiError();
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
	public Response getProductsByCategory(int categoryId, String productType) {
		if(categoryId == 0) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(1001);
			apiError.setErrorDescription("Category Id mandatory" );
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(apiError).build();
		}
		ProductGenericDao productDao = new ProductGenericDaoImpl();
		try {
			List<Product> products = null;
			if(StringUtils.isNotBlank(productType)) {
				//Sending list of queryParams and list of paramValues
				products = productDao.getAllByQueryParams(Arrays.asList("category.categoryId", "productType"), Arrays.asList(categoryId, productType));
			}
			else {
				products = productDao.getAllByQueryParams(Arrays.asList("category.categoryId"), Arrays.asList(categoryId));
			}
			return Response.status(HttpStatus.SC_OK).entity(products).build();
		}
		catch (Exception e) {
			return ProductUtils.setApiError();
		}
	}

	@Override
	public Response getProductsInPriceRange(String productType, int priceMin, int priceMax) {
		if(priceMax < priceMin) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(1001);
			apiError.setErrorDescription("Max can't be less than min" );
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(apiError).build();
		}
		ProductGenericDao productDao = new ProductGenericDaoImpl();
		try {
			List<Product> products = productDao.getAllInRange(productType, "price", BigDecimal.valueOf(priceMin), BigDecimal.valueOf(priceMax));
			return Response.status(HttpStatus.SC_OK).entity(products).build();
		}
		catch (Exception e) {
			return ProductUtils.setApiError();
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
			product.setCategoryName(product.getCategory().getDisplayName());
			responseProducts.add(product);
			List<Product> products = productDao.getRelatedProducts(product, expectedCount);
			responseProducts.addAll(products);
			return Response.status(HttpStatus.SC_OK).entity(responseProducts).build();
		}
		catch (Exception e) {
			return ProductUtils.setApiError();
		}
	}

}
