package com.abirami.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface FormatResource {

	@GET
	@Path("/")
	public Response getAllFormats();
	
	@GET
	@Path("/{formatId}")
	public Response getFormat(@PathParam("formatId") int formatId);
	
}
