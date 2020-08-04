package com.abirami.api.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpStatus;

import com.abirami.api.ProductResource;
import com.abirami.dao.GenericDao;
import com.abirami.dao.impl.GenericDaoImpl;
import com.abirami.model.ApiError;
import com.abirami.model.Category;
import com.abirami.model.Product;

/**
 * @author vicky
 *
 */

@Path("products")
@Consumes({"application/json"})
@Produces({"application/json"})
public class ProductResourceImpl implements ProductResource {

	@Override
	public Response getProducts() {
		GenericDao productDao = new GenericDaoImpl();
		List<Product> products = new ArrayList<Product>();
		try {
			products = productDao.getAll(Product.class);
			return Response.status(HttpStatus.SC_OK).entity(products).build();
		}
		catch (Exception e) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(2001);
			apiError.setErrorDescription("server error" );
			return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(apiError).build();
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
		GenericDao productDao = new GenericDaoImpl();
		try {
			Product product = productDao.get(Product.class, productId);
			product.setCategoryName(product.getCategory().getDisplayName());
			return Response.status(HttpStatus.SC_OK).entity(product).build();
		}
		catch (Exception e) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(2001);
			apiError.setErrorDescription("server error" );
			return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(apiError).build();
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
		GenericDao productDao = new GenericDaoImpl();
		try {
			//get Category by categoryId and set in the product before saving the product.
			product.setCategory(productDao.get(Category.class, Integer.valueOf(product.getCategoryName())));
			int id = productDao.save(product);
			product.setProductId(id);
			//setting the correct product name in the response
			product.setCategoryName(product.getCategory().getDisplayName());
			return Response.status(HttpStatus.SC_OK).entity(product).build();
		}
		catch (Exception e) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(2001);
			apiError.setErrorDescription("server error" );
			return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(apiError).build();
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
	public Response getProductsByCategory(int categoryId) {
		if(categoryId == 0) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(1001);
			apiError.setErrorDescription("Category Id mandatory" );
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(apiError).build();
		}
		GenericDao productDao = new GenericDaoImpl();
		try {
			List<Product> products = productDao.getAllByForeignKey(Product.class, "category.categoryId", categoryId);
			return Response.status(HttpStatus.SC_OK).entity(products).build();
		}
		catch (Exception e) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(2001);
			apiError.setErrorDescription("server error" );
			return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(apiError).build();
		}
	}

	@Override
	public Response getProductsInPriceRange(int priceMin, int priceMax) {
		if(priceMax < priceMin) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(1001);
			apiError.setErrorDescription("Max can't be less than min" );
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(apiError).build();
		}
		GenericDao productDao = new GenericDaoImpl();
		try {
			List<Product> products = productDao.getAllByInRange(Product.class, "price", BigDecimal.valueOf(priceMin), BigDecimal.valueOf(priceMax));
			return Response.status(HttpStatus.SC_OK).entity(products).build();
		}
		catch (Exception e) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(2001);
			apiError.setErrorDescription("server error" );
			return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(apiError).build();
		}
	}

}
