package com.abirami.util;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpStatus;

import com.abirami.model.ApiError;

public class ProductUtils {

	public static Response setApiServerError(Integer errorCode, String errorMsg) {
		ApiError apiError = new ApiError();
		apiError.setErrorCode(errorCode);
		apiError.setErrorDescription(errorMsg);
		return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(apiError).build();
	}
	
	public static Response setApiBadRequestError(int code) {
		ApiError apiError = new ApiError();
		apiError.setErrorCode(code);
		apiError.setErrorDescription("server error" );
		return Response.status(HttpStatus.SC_BAD_REQUEST).entity(apiError).build();
	}
	
	public static void setServletError(HttpServletRequest req, Response response) {
		ApiError error = response.readEntity(new GenericType<ApiError>() {});
		System.out.println("ERROR! " + response.getStatus());    
		System.out.println(response.getStatus() + " : " +response.getStatusInfo().getReasonPhrase());
		req.setAttribute("errorCode", error.getErrorCode());
		req.setAttribute("errorDesc", error.getErrorDescription());
	}
}
