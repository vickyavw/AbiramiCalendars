package com.abirami.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;

import com.abirami.model.Category;
import com.abirami.model.Product;
import com.abirami.util.ApiConstants;
import com.abirami.util.ProductType;
import com.abirami.util.ProductUtils;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;

@WebServlet(name = "ProductServlet", urlPatterns = {"/calendars", "/diaries", "/boxes", "/labels" }, loadOnStartup = 1) 
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Map<String, String> URL_PRODUCT_TYPE_MAP = new HashMap<String, String>();
	static {
		URL_PRODUCT_TYPE_MAP.put("/calendars", ProductType.CALENDAR.toString());
		URL_PRODUCT_TYPE_MAP.put("/diaries", ProductType.DIARY.toString());
		URL_PRODUCT_TYPE_MAP.put("/boxes", ProductType.BOX.toString());
		URL_PRODUCT_TYPE_MAP.put("/labels", ProductType.LABEL.toString());
	}
       
    public ProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		setDefaultValuesInRequest(req);
		
		//if category filter is selected
		if(req.getParameter("categoryId") != null) {
			getCalendarsByCategory(req);
			req.getRequestDispatcher("products.jsp").forward(req, res);
			return;
		}
		//if price filter is selected
		else if(req.getParameter("priceFilter") != null) {
			getCalendarsInPriceRange(req);
			req.getRequestDispatcher("products.jsp").forward(req, res);
			return;
		}
		//to view individual product
		else if(null != req.getParameter("productId")) {
			getProductById(req);
			req.getRequestDispatcher("product-details.jsp").forward(req, res);
    		return;
		}
		//view all products
		else {
			getAllProducts(req);
			req.getRequestDispatcher("products.jsp").forward(req, res);
			return;
		}
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
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
			ProductUtils.setServletError(req, response);
		}
	}
	
	private void getCalendarsByCategory(HttpServletRequest req) throws IOException {
		String categoryId = req.getParameter("categoryId");
		if(null == categoryId || !StringUtils.isNumeric(categoryId)) {
			categoryId = "1";
		}
		
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.GET_PRODUCTS_BY_CATEGORY_API_URL.replace(ApiConstants.CATEGORY_ID_REPLACE, categoryId))
										.queryParam(ApiConstants.PRODUCT_TYPE_QUERY_PARAM, URL_PRODUCT_TYPE_MAP.get(req.getRequestURI()));
		
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
			ProductUtils.setServletError(req, response);
		}
		
	}
	
	private void getCalendarsInPriceRange(HttpServletRequest req) throws IOException {
		String priceMin = req.getParameter("priceMin");
		String priceMax = req.getParameter("priceMax");
		//setting to show the values selected after loading the screen
		req.setAttribute("priceMinSel", priceMin);
		req.setAttribute("priceMaxSel", priceMax);
		if(!StringUtils.isNumeric(priceMin) || !StringUtils.isNumeric(priceMax)) {
			req.setAttribute("products",null);
			return;
		}
		
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.GET_PRODUCTS_BY_PRICE_API_URL)
										.queryParam(ApiConstants.PRODUCT_TYPE_QUERY_PARAM, URL_PRODUCT_TYPE_MAP.get(req.getRequestURI()))
										.queryParam(ApiConstants.PRICE_MIN_QUERY_PARAM, priceMin)
										.queryParam(ApiConstants.PRICE_MAX_QUERY_PARAM, priceMax);
		
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
			ProductUtils.setServletError(req, response);
		}
		
	}
	
	private void getProductById(HttpServletRequest req) throws IOException {
		String productId = req.getParameter("productId");
		String relatedProducts = req.getParameter("getRelated");
		int relatedProductsCount = 4;
		if(StringUtils.isEmpty(productId)) {
			productId = "0";
		}
		
		Client client = ClientBuilder.newClient();
		WebTarget resource = null;
		if(null == relatedProducts) {
			resource = client.target(ApiConstants.GET_PRODUCT_BY_ID_API_URL.replace(ApiConstants.PRODUCT_ID_REPLACE, productId))
					.queryParam(ApiConstants.PRODUCT_TYPE_QUERY_PARAM, URL_PRODUCT_TYPE_MAP.get(req.getRequestURI()));
		
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
				ProductUtils.setServletError(req, response);
			}
		}
		else {
			if(StringUtils.isNumeric(relatedProducts)) {
				relatedProductsCount = Integer.valueOf(relatedProducts);
			}
			getRelatedProducts(req, productId, relatedProductsCount);
		}
	}
	
	private void getRelatedProducts(HttpServletRequest req, String productId, int relatedProductsCount) throws IOException {
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.GET_RELATED_PRODUCTS_OF_ID_URL.replace(ApiConstants.PRODUCT_ID_REPLACE, productId))
										.queryParam(ApiConstants.PRODUCT_TYPE_QUERY_PARAM, URL_PRODUCT_TYPE_MAP.get(req.getRequestURI()))
										.queryParam(ApiConstants.ROWS_TO_FETCH, relatedProductsCount);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    List<Product> responseMap = response.readEntity(new GenericType<List<Product>>() {});
		    Product product = responseMap.get(0);
		    if(null != product){
	    		setBase64Image(product);
	    		req.setAttribute("product", product);
	    	}
		    responseMap.remove(0);
	    	for(Product relatedProducts : responseMap) {
	    		setBase64Image(relatedProducts);
	    	}
		    req.setAttribute("relatedProducts", responseMap);
		} else {
			ProductUtils.setServletError(req, response);
		}
		
	}

	private void getAllProducts(HttpServletRequest req) throws IOException{
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.GET_ALL_PRODUCTS_API_URL)
										.queryParam(ApiConstants.PRODUCT_TYPE_QUERY_PARAM, URL_PRODUCT_TYPE_MAP.get(req.getRequestURI()));
		
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
			ProductUtils.setServletError(req, response);
		}
	}

	private void setDefaultValuesInRequest(HttpServletRequest req) {
		//set default values in request
		req.setAttribute("currentProductUri", req.getRequestURI());
		req.setAttribute("currentProduct", req.getRequestURI().substring(1, 2).toUpperCase() + req.getRequestURI().substring(2));
		req.setAttribute("priceMin", "1");
		req.setAttribute("priceMax", "99");
		req.setAttribute("relatedProducts", null);
		req.setAttribute("products", null);
		req.setAttribute("product", null);
	}

}
