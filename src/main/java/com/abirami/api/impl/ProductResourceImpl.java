package com.abirami.api.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpStatus;

import com.abirami.api.ProductResource;
import com.abirami.dao.ProductDao;
import com.abirami.dao.impl.ProductDaoImpl;
import com.abirami.model.ApiError;
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
		ProductDao productDao = new ProductDaoImpl();
		List<Product> products = new ArrayList<Product>();
		try {
			products = productDao.getProducts();
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
		ProductDao productDao = new ProductDaoImpl();
		try {
			return Response.status(HttpStatus.SC_OK).entity(productDao.getProduct(productId)).build();
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
		if(null == product) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(1001);
			apiError.setErrorDescription("Product mandatory" );
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(apiError).build();
		}
		ProductDao productDao = new ProductDaoImpl();
		try {
			int id = productDao.addProduct(product);
			product.setProductId(id);
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

}
