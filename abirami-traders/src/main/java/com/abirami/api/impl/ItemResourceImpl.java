package com.abirami.api.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpStatus;

import com.abirami.api.ItemResource;
import com.abirami.model.Item;

/**
 * @author vicky
 *
 */

@Path("items")
@Consumes({"application/json"})
@Produces({"application/json"})
public class ItemResourceImpl implements ItemResource {

	@Override
	public Response getItems() {
		// TODO Read from db
		List<Item> items = new ArrayList<Item>();
		Item item1 = new Item();
		item1.setItemId(1L);
		item1.setDisplayName("Calendar 1");
		item1.setDescription("Monthly 12 sheeter calendar 1");
		
		Item item2 = new Item();
		item2.setItemId(2L);
		item2.setDisplayName("Calendar 2");
		item2.setDescription("Monthly 6 sheeter calendar 2");
		
		items.add(item1);
		items.add(item2);
		return Response.status(HttpStatus.SC_ACCEPTED).entity(items).build();
	}

	@Override
	public Response getItem(Long itemId) {
		// TODO Read from db
		return null;
	}

	@Override
	public Response addItem(Item item) {
		// TODO update db
		return null;
	}

	@Override
	public Response updateItem(int itemId, Item item) {
		// TODO update db
		return null;
	}

	@Override
	public Response deleteItem(int itemId) {
		// TODO update db
		return null;
	}

}
