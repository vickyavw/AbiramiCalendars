package com.abirami.util;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpStatus;

import com.abirami.model.ApiError;

public class ProductUtils {

	public static Response setApiServerError() {
		ApiError apiError = new ApiError();
		apiError.setErrorCode(2001);
		apiError.setErrorDescription("server error" );
		return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(apiError).build();
	}
	
	public static Response setApiBadRequestError(int code) {
		ApiError apiError = new ApiError();
		apiError.setErrorCode(code);
		apiError.setErrorDescription("server error" );
		return Response.status(HttpStatus.SC_BAD_REQUEST).entity(apiError).build();
	}
	
	public static void setServletError(HttpServletRequest req, Response response) {
		ApiError error = response.readEntity(ApiError.class);
		System.out.println("ERROR! " + response.getStatus());    
		System.out.println(response.getStatus() + " : " +response.getStatusInfo().getReasonPhrase());
		req.setAttribute("errorCode", error.getErrorCode());
		req.setAttribute("errorDesc", error.getErrorDescription());
	}
}
