package com.abirami.api.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpStatus;
import org.hibernate.HibernateException;

import com.abirami.api.FormatResource;
import com.abirami.dao.FormatGenericDao;
import com.abirami.dao.impl.FormatGenericDaoImpl;
import com.abirami.model.ApiError;
import com.abirami.util.ProductUtils;

@Path("formats")
@Consumes({"application/json"})
@Produces({"application/json"})
public class FormatResourceImpl implements FormatResource {

	@Override
	public Response getAllFormats() {
		FormatGenericDao formatCategoryDao = new FormatGenericDaoImpl();
		try {
			return Response.status(HttpStatus.SC_OK).entity(formatCategoryDao.getAll()).build();
		}
		catch(HibernateException he) {
			return ProductUtils.setApiServerError(3001,he.getMessage());
		}
		catch (Exception e) {
			return ProductUtils.setApiServerError(3002,e.getMessage());
		}
	}

	@Override
	public Response getFormat(int formatId) {
		if(formatId == 0) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(1001);
			apiError.setErrorDescription("formatCategoryId Id mandatory" );
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(apiError).build();
		}
		FormatGenericDao formatCategoryDao = new FormatGenericDaoImpl();
		try {
			return Response.status(HttpStatus.SC_OK).entity(formatCategoryDao.get(formatId)).build();
		}
		catch(HibernateException he) {
			return ProductUtils.setApiServerError(3001,he.getMessage());
		}
		catch (Exception e) {
			return ProductUtils.setApiServerError(3002,e.getMessage());
		}
	}

}
