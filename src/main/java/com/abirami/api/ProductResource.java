package com.abirami.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.abirami.model.Product;

/**
 * @author vicky
 *
 */
public interface ProductResource {
	@GET
	@Path("/")
	public Response getProducts(@QueryParam("productType") String productType, 
			@QueryParam("sortBy") @DefaultValue("productName") String sortBy, 
			@QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
			@QueryParam("pageSize") @DefaultValue("50") Integer pageSize,
			@QueryParam("pageNumber") @DefaultValue("1") Integer pageNumber
			);
	
	@GET
	@Path("/{productId}")
	public Response getProduct(@PathParam("productId") int productId);
	
	@POST
	@Path("/")
	public Response addProduct(Product product);
	
	@PUT
	@Path("/{productId}")
	public Response updateProduct(@PathParam("productId") int productId, Product product);
	
	@DELETE
	@Path("/{productId}")
	public Response deleteProduct(@PathParam("productId") int productId);
	
	@GET
	@Path("/query")
	public <T> Response getProductsByCriteria(@QueryParam("productType") String productType, 
			@QueryParam("queryParams") String queryParams,
			@QueryParam("sortBy") @DefaultValue("productName") String sortBy, 
			@QueryParam("sortDirection") @DefaultValue("asc") String sortDirection,
			@QueryParam("pageSize") @DefaultValue("50") Integer pageSize,
			@QueryParam("pageNumber") @DefaultValue("1") Integer pageNumber);
	
	@GET
	@Path("/related/{productId}")
	public Response getProductWithRelatedProductsInfo(@PathParam("productId") int productId, @QueryParam("expectedCount") int expectedCount);
	
}
