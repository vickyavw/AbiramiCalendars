package com.abirami.api.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpStatus;
import org.springframework.stereotype.Component;

import com.abirami.api.ItemResource;
import com.abirami.dao.ItemDao;
import com.abirami.dao.impl.ItemDaoImpl;
import com.abirami.model.Item;

/**
 * @author vicky
 *
 */

@Path("items")
@Consumes({"application/json"})
@Produces({"application/json"})
@Component
public class ItemResourceImpl implements ItemResource {

	@Override
	public Response getItems() {
		ItemDao itemDao = new ItemDaoImpl();
		List<Item> items = new ArrayList<Item>();
		items = itemDao.getItems();
		return Response.status(HttpStatus.SC_OK).entity(items).build();
	}

	@Override
	public Response getItem(int itemId) {
		ItemDao itemDao = new ItemDaoImpl();
		return Response.status(HttpStatus.SC_OK).entity(itemDao.getItem(itemId)).build();
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
