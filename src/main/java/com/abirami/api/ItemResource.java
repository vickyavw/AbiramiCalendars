package com.abirami.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.abirami.model.Item;

/**
 * @author vicky
 *
 */
public interface ItemResource {
	@GET
	@Path("/")
	public Response getItems();
	
	@GET
	@Path("/{itemId}")
	public Response getItem(@PathParam("itemId") Long itemId);
	
	@POST
	@Path("/")
	public Response addItem(Item item);
	
	@PUT
	@Path("/{itemId}")
	public Response updateItem(@PathParam("itemId") int itemId, Item item);
	
	@DELETE
	@Path("/{itemId}")
	public Response deleteItem(@PathParam("itemId") int itemId);
	
}
