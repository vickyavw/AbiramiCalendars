package com.abirami.api.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpStatus;

import com.abirami.api.ItemResource;
import com.abirami.dao.ItemDao;
import com.abirami.dao.impl.ItemDaoImpl;
import com.abirami.model.ApiError;
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
		ItemDao itemDao = new ItemDaoImpl();
		List<Item> items = new ArrayList<Item>();
		try {
			items = itemDao.getItems();
			return Response.status(HttpStatus.SC_OK).entity(items).build();
		}
		catch (Exception e) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(2001);
			apiError.setErrorDescription("server error" );
			return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(apiError).build();
		}
	}

	@Override
	public Response getItem(int itemId) {
		if(itemId == 0) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(1001);
			apiError.setErrorDescription("Item Id mandatory" );
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(apiError).build();
		}
		ItemDao itemDao = new ItemDaoImpl();
		try {
			return Response.status(HttpStatus.SC_OK).entity(itemDao.getItem(itemId)).build();
		}
		catch (Exception e) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(2001);
			apiError.setErrorDescription("server error" );
			return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(apiError).build();
		}
	}

	@Override
	public Response addItem(Item item) {
		if(null == item) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(1001);
			apiError.setErrorDescription("Item mandatory" );
			return Response.status(HttpStatus.SC_BAD_REQUEST).entity(apiError).build();
		}
		ItemDao itemDao = new ItemDaoImpl();
		try {
			int id = itemDao.addItem(item);
			item.setItemId(id);
			return Response.status(HttpStatus.SC_OK).entity(item).build();
		}
		catch (Exception e) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(2001);
			apiError.setErrorDescription("server error" );
			return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(apiError).build();
		}
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
