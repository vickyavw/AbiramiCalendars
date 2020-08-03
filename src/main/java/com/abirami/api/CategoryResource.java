package com.abirami.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.abirami.model.Category;

public interface CategoryResource {

	@GET
	@Path("/")
	public Response getCategories();
	
	@GET
	@Path("/{categoryId}")
	public Response getCategory(@PathParam("categoryId") int categoryId);
	
	@POST
	@Path("/")
	public Response addCategory(Category category);
	
	@PUT
	@Path("/{categoryId}")
	public Response updateCategory(@PathParam("categoryId") int categoryId, Category category);
	
	@DELETE
	@Path("/{categoryId}")
	public Response deleteCategory(@PathParam("categoryId") int categoryId);
	
}
