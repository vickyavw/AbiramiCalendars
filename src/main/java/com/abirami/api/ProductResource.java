package com.abirami.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.abirami.model.Product;

/**
 * @author vicky
 *
 */
public interface ProductResource {
	@GET
	@Path("/")
	public Response getProducts();
	
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
	@Path("/category/{categoryId}")
	public Response getProductsByCategory(@PathParam("categoryId") int categoryId);
	
	@GET
	@Path("/price/{priceMin}/{priceMax}")
	public Response getProductsInPriceRange(@PathParam("priceMin") int priceMin, @PathParam("priceMax") int priceMax);
	
}
