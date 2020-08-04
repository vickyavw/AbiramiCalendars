package com.abirami.api.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpStatus;

import com.abirami.api.CategoryResource;
import com.abirami.dao.GenericDao;
import com.abirami.dao.impl.GenericDaoImpl;
import com.abirami.model.ApiError;
import com.abirami.model.Category;

@Path("categories")
@Consumes({"application/json"})
@Produces({"application/json"})
public class CategoryResourceImpl implements CategoryResource {

	@Override
	public Response getCategories() {
		GenericDao categoryDao = new GenericDaoImpl();
		List<Category> categories = new ArrayList<Category>();
		try {
			categories = categoryDao.getAll(Category.class);
			return Response.status(HttpStatus.SC_OK).entity(categories).build();
		}
		catch (Exception e) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(2001);
			apiError.setErrorDescription("server error" );
			return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(apiError).build();
		}
	}

	@Override
	public Response getCategory(int categoryId) {
		if(categoryId == 0) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(1001);
			apiError.setErrorDescription("Category Id mandatory" );
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(apiError).build();
		}
		GenericDao categoryDao = new GenericDaoImpl();
		try {
			return Response.status(HttpStatus.SC_OK).entity(categoryDao.get(Category.class, categoryId)).build();
		}
		catch (Exception e) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(2001);
			apiError.setErrorDescription("server error" );
			return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(apiError).build();
		}
	}

	@Override
	public Response addCategory(Category category) {
		if(null == category) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(1001);
			apiError.setErrorDescription("Category mandatory" );
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(apiError).build();
		}
		GenericDao categoryDao = new GenericDaoImpl();
		try {
			int id = categoryDao.save(category);
			category.setCategoryId(id);
			return Response.status(HttpStatus.SC_OK).entity(category).build();
		}
		catch (Exception e) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(2001);
			apiError.setErrorDescription("server error" );
			return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(apiError).build();
		}
	}

	@Override
	public Response updateCategory(int categoryId, Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteCategory(int categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

}
