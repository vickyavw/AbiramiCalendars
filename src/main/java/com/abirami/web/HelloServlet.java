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
import com.abirami.model.Product;
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
		
		String productId = req.getParameter("productId");
		String apiUrl = "http://localhost:8080/api/products";
		if(null != productId) {
			if(StringUtils.isEmpty(productId)) {
				productId = "0";
			}
			apiUrl = apiUrl+"/"+productId;
		}
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(apiUrl);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    List<Product> products = new ArrayList<Product>();
		    if(StringUtils.isNotEmpty(productId)) {
		    	Product product = response.readEntity(Product.class);
		    	if(null != product){
		    		setBase64Image(product);
		    		req.setAttribute("product", product);
		    	}
		    }
		    else {
		    	products = response.readEntity(new GenericType<List<Product>>() {});
		    	for(Product product : products) {
		    		setBase64Image(product);
		    	}
		    	req.setAttribute("products", products);
		    }
		} else {
			ApiError error = response.readEntity(ApiError.class);
		    System.out.println("ERROR! " + response.getStatus());    
		    System.out.println(response.getStatus() + " : " +response.getStatusInfo().getReasonPhrase());
		    req.setAttribute("errorCode", error.getErrorCode());
		    req.setAttribute("errorDesc", error.getErrorDescription());
		}
		req.getRequestDispatcher("products.jsp").forward(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String name = req.getParameter("productName");
		String desc = req.getParameter("productDesc");
		String apiUrl = "http://localhost:8080/api/products";
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(apiUrl);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);
		
		Product product = new Product();
		product.setDisplayName(name);
		product.setDescription(desc);
		InputStream stream;
		if(req.getPart("file").getSize()>0){
			stream = req.getPart("file").getInputStream();
			product.setImage(ByteStreams.toByteArray(stream));
		}
		Response response = request.post(Entity.entity(product,MediaType.APPLICATION_JSON),Response.class);
		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    product = response.readEntity(Product.class);
		    List<Product> products = new ArrayList<Product>();
	    	if(null != product){
	    		if(null != product.getImage()) {
	    			product.setBase64Image(DatatypeConverter.printBase64Binary(product.getImage()));
	    		}
	    		products.add(product);
	    	}
	    	req.setAttribute("products", products);
		}
		req.getRequestDispatcher("products.jsp").forward(req, res);
	}

	private void setBase64Image(Product product) throws IOException {
		if(null != product.getImage() && product.getImage().length>0) {
			product.setBase64Image(DatatypeConverter.printBase64Binary(product.getImage()));
		}
		else {
			InputStream stream = getServletContext().getResourceAsStream("/images/no-image.jpg"); 
			product.setBase64Image(BaseEncoding.base64().encode(ByteStreams.toByteArray(stream)));
		}
	}

}
