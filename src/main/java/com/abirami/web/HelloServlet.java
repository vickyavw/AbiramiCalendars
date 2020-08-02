package com.abirami.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;

import com.abirami.model.ApiError;
import com.abirami.model.Item;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;

@WebServlet(name = "UsersServlet", urlPatterns = {"user"}, loadOnStartup = 1) 
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String itemId = req.getParameter("itemId");
		String apiUrl = "http://localhost:8080/api/items";
		if(null != itemId) {
			if(StringUtils.isEmpty(itemId)) {
				itemId = "0";
			}
			apiUrl = apiUrl+"/"+itemId;
		}
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(apiUrl);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    List<Item> items = new ArrayList<Item>();
		    if(StringUtils.isNotEmpty(itemId)) {
		    	Item item = response.readEntity(Item.class);
		    	if(null != item){
		    		setBase64Image(item);
		    		req.setAttribute("item", item);
		    	}
		    }
		    else {
		    	items = response.readEntity(new GenericType<List<Item>>() {});
		    	for(Item item : items) {
		    		setBase64Image(item);
		    	}
		    	req.setAttribute("items", items);
		    }
		} else {
			ApiError error = response.readEntity(ApiError.class);
		    System.out.println("ERROR! " + response.getStatus());    
		    System.out.println(response.getStatus() + " : " +response.getStatusInfo().getReasonPhrase());
		    req.setAttribute("errorCode", error.getErrorCode());
		    req.setAttribute("errorDesc", error.getErrorDescription());
		}
		req.getRequestDispatcher("items.jsp").forward(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String name = req.getParameter("itemName");
		String desc = req.getParameter("itemDesc");
		String apiUrl = "http://localhost:8080/api/items";
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(apiUrl);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);
		
		Item item = new Item();
		item.setDisplayName(name);
		item.setDescription(desc);
		InputStream stream;
		if(req.getPart("file").getSize()>0){
			stream = req.getPart("file").getInputStream();
			item.setImage(ByteStreams.toByteArray(stream));
		}
		Response response = request.post(Entity.entity(item,MediaType.APPLICATION_JSON),Response.class);
		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    item = response.readEntity(Item.class);
		    List<Item> items = new ArrayList<Item>();
	    	if(null != item){
	    		if(null != item.getImage()) {
	    			item.setBase64Image(DatatypeConverter.printBase64Binary(item.getImage()));
	    		}
	    		items.add(item);
	    	}
	    	req.setAttribute("items", items);
		}
		req.getRequestDispatcher("items.jsp").forward(req, res);
	}

	private void setBase64Image(Item item) throws IOException {
		if(null != item.getImage() && item.getImage().length>0) {
			item.setBase64Image(DatatypeConverter.printBase64Binary(item.getImage()));
		}
		else {
			InputStream stream = getServletContext().getResourceAsStream("/images/no-image.jpg"); 
			item.setBase64Image(BaseEncoding.base64().encode(ByteStreams.toByteArray(stream)));
		}
	}

}
