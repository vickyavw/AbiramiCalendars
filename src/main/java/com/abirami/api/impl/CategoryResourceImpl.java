package com.abirami.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpStatus;
import org.hibernate.HibernateException;

import com.abirami.api.CategoryResource;
import com.abirami.dao.CategoryGenericDao;
import com.abirami.dao.impl.CategoryGenericDaoImpl;
import com.abirami.model.ApiError;
import com.abirami.model.Category;
import com.abirami.model.CategoryDTO;
import com.abirami.model.PaginatedCategoriesApiResponse;
import com.abirami.util.ProductUtils;

@Path("categories")
@Consumes({"application/json"})
@Produces({"application/json"})
public class CategoryResourceImpl implements CategoryResource {

	@Override
	public Response getCategories() {
		CategoryGenericDao categoryDao = new CategoryGenericDaoImpl();
		try {
			PaginatedCategoriesApiResponse response = categoryDao.getAll();
			return Response.status(HttpStatus.SC_OK).entity(response).build();
		}
		catch(HibernateException he) {
			return ProductUtils.setApiServerError(2001,he.getMessage());
		}
		catch (Exception e) {
			return ProductUtils.setApiServerError(2002,e.getMessage());
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
		CategoryGenericDao categoryDao = new CategoryGenericDaoImpl();
		try {
			CategoryDTO category = new CategoryDTO(categoryDao.get(categoryId));
			return Response.status(HttpStatus.SC_OK).entity(category).build();
		}
		catch(HibernateException he) {
			return ProductUtils.setApiServerError(2001,he.getMessage());
		}
		catch (Exception e) {
			return ProductUtils.setApiServerError(2002,e.getMessage());
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
		CategoryGenericDao categoryDao = new CategoryGenericDaoImpl();
		try {
			int id = categoryDao.save(category);
			category.setCategoryId(id);
			return Response.status(HttpStatus.SC_OK).entity(category).build();
		}
		catch(HibernateException he) {
			return ProductUtils.setApiServerError(2001,he.getMessage());
		}
		catch (Exception e) {
			return ProductUtils.setApiServerError(2002,e.getMessage());
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
