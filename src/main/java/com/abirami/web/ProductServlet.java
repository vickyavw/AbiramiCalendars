package com.abirami.web;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
import org.apache.commons.lang3.math.NumberUtils;

import com.abirami.model.ApiError;
import com.abirami.model.Category;
import com.abirami.model.Product;
import com.abirami.util.ApiConstants;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;

@WebServlet(name = "ProductServlet", urlPatterns = {"/calendars", "/diaries", "/boxes", "/labels", "/customize", "/contact", "/admin-product"}, loadOnStartup = 1) 
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//If Admin request
		if(req.getRequestURI().equals("/admin-product")) {
			//get categories to load the admin add product's category drop down
			getCategories(req);
			req.getRequestDispatcher("admin-product.jsp").forward(req, res);
    		return;
		}
		//if category filter is selected
		else if(req.getRequestURI().equals("/calendars") && req.getParameter("categoryId") != null) {
			getCalendarsByCategory(req);
			req.getRequestDispatcher("products.jsp").forward(req, res);
			return;
		}
		//if price filter is selected
		else if(req.getRequestURI().equals("/calendars") && req.getParameter("priceFilter") != null) {
			getCalendarsInPriceRange(req);
			req.getRequestDispatcher("products.jsp").forward(req, res);
			return;
		}
		else if(null != req.getParameter("productId")) {
			getProductById(req);
			req.getRequestDispatcher("product-details.jsp").forward(req, res);
    		return;
		}
		else {
			getAllProducts(req);
			req.getRequestDispatcher("products.jsp").forward(req, res);
			return;
		}
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String name = req.getParameter("productName");
		String desc = req.getParameter("productDesc");
		String categoryId = req.getParameter("categoryId");
		String price = req.getParameter("price");
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.PRODUCTS_API_URL);
		
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
		if(price == null || !NumberUtils.isCreatable(price))
			product.setPrice(new BigDecimal("10.50"));
		else
			product.setPrice(new BigDecimal(price));
		
		if(null == categoryId || !StringUtils.isNumeric(categoryId)) {
			categoryId = "1";
		}
		
		//Sending categoryId as name because of JsonIgnore on category object due to cyclic dependency
		product.setCategoryName(categoryId);
		
		Response response = request.post(Entity.entity(product,MediaType.APPLICATION_JSON),Response.class);
		
		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    product = response.readEntity(Product.class);
	    	if(null != product){
	    		if(null != product.getImage()) {
	    			product.setBase64Image(DatatypeConverter.printBase64Binary(product.getImage()));
	    		}
	    	}
	    	req.setAttribute("product", product);
		}
		req.getRequestDispatcher("product-details.jsp").forward(req, res);
	}

	private void setBase64Image(Product product) throws IOException {
		if(null != product.getImage() && product.getImage().length>0) {
			product.setBase64Image(DatatypeConverter.printBase64Binary(product.getImage()));
		}
		else {
			InputStream stream = getServletContext().getResourceAsStream("/images/product/no-image.jpg"); 
			product.setBase64Image(BaseEncoding.base64().encode(ByteStreams.toByteArray(stream)));
		}
	}

	private void getCategories(HttpServletRequest req) {
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.CATEGORIES_API_URL);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);
		
		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
			List<Category> categories = new ArrayList<Category>();
		    System.out.println("Success! " + response.getStatus());
		    categories = response.readEntity(new GenericType<List<Category>>() {});
		    req.setAttribute("categories", categories);
		} else {
			setApiError(req, response);
		}
	}
	
	private void getCalendarsByCategory(HttpServletRequest req) throws IOException {
		String categoryId = req.getParameter("categoryId");
		if(null == categoryId || !StringUtils.isNumeric(categoryId)) {
			categoryId = "1";
		}
		
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.GET_PRODUCTS_BY_CATEGORY_API_URL + "/" +categoryId);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);
		
		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
			List<Product> products = new ArrayList<Product>();
		    System.out.println("Success! " + response.getStatus());
		    products = response.readEntity(new GenericType<List<Product>>() {});
		    //get categories to set in the filters
		    getCategories(req);
	    	for(Product product : products) {
	    		setBase64Image(product);
	    	}
		    req.setAttribute("products", products);
		} else {
			setApiError(req, response);
		}
		
	}
	
	private void getCalendarsInPriceRange(HttpServletRequest req) throws IOException {
		String priceMin = req.getParameter("priceMin").substring(3);//remove Rs.
		String priceMax = req.getParameter("priceMax").substring(3);
		if(!StringUtils.isNumeric(priceMin) || !StringUtils.isNumeric(priceMax)) {
			req.setAttribute("products",null);
			return;
		}
		
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.GET_PRODUCTS_BY_PRICE_API_URL + "/" +priceMin + "/" +priceMax);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);
		
		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
			List<Product> products = new ArrayList<Product>();
		    System.out.println("Success! " + response.getStatus());
		    products = response.readEntity(new GenericType<List<Product>>() {});
		    //get categories to set in the filters
		    getCategories(req);
	    	for(Product product : products) {
	    		setBase64Image(product);
	    	}
		    req.setAttribute("products", products);
		} else {
			setApiError(req, response);
		}
		
	}
	
	private void getProductById(HttpServletRequest req) throws IOException {
		String productId = req.getParameter("productId");
		if(StringUtils.isEmpty(productId)) {
			productId = "0";
		}
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.PRODUCTS_API_URL+"/"+productId);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
	    	Product product = response.readEntity(Product.class);
	    	if(null != product){
	    		setBase64Image(product);
	    		req.setAttribute("product", product);
	    	}
		} else {
			setApiError(req, response);
		}
	}
	
	private void getAllProducts(HttpServletRequest req) throws IOException{
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.PRODUCTS_API_URL);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    List<Product> products = new ArrayList<Product>();
	    	products = response.readEntity(new GenericType<List<Product>>() {});
	    	//get categories to set in the filters
	    	getCategories(req);
	    	for(Product product : products) {
	    		setBase64Image(product);
	    	}
	    	req.setAttribute("products", products);
		} else {
			setApiError(req, response);
		}
	}

	private void setApiError(HttpServletRequest req, Response response) {
		ApiError error = response.readEntity(ApiError.class);
		System.out.println("ERROR! " + response.getStatus());    
		System.out.println(response.getStatus() + " : " +response.getStatusInfo().getReasonPhrase());
		req.setAttribute("errorCode", error.getErrorCode());
		req.setAttribute("errorDesc", error.getErrorDescription());
	}

}
