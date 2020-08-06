package com.abirami.web;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.abirami.model.CategoriesApiResponse;
import com.abirami.model.CategoryDTO;
import com.abirami.model.Product;
import com.abirami.model.ProductDTO;
import com.abirami.model.ProductsApiResponse;
import com.abirami.util.ApiConstants;
import com.abirami.util.ProductType;
import com.abirami.util.ProductUtils;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;

@WebServlet(name = "AdminProductServlet", urlPatterns = {"/customize", "/contact", "/admin-product"}, loadOnStartup = 1) 
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class AdminProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setAttribute("currentProductUri", req.getRequestURI());
		req.setAttribute("currentProduct", req.getRequestURI().substring(1, 2).toUpperCase() + req.getRequestURI().substring(2));
		if(null != req.getParameter("productId")) {
			//get product for admin
			getProductById(req);
			req.getRequestDispatcher("product-details.jsp").forward(req, res);
    		return;
		}
		else if ("true".equals(req.getParameter("getAll"))) {
			//get all products for admin
			getAllProducts(req);
			req.getRequestDispatcher("products.jsp").forward(req, res);
			return;
		}
		else {
			//If Admin request
			//get categories to load the admin add product's category drop down
			getCategories(req);
			//set productTypes
			req.setAttribute("productTypes", Arrays.asList(ProductType.values()));
			req.getRequestDispatcher("admin-product.jsp").forward(req, res);
    		return;
		}
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String name = req.getParameter("productName");
		String desc = req.getParameter("productDesc");
		String categoryId = req.getParameter("categoryId");
		String productType = req.getParameter("productType");
		String price = req.getParameter("price");
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.GET_ALL_PRODUCTS_API_URL);
		
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
		product.setProductType(ProductType.valueOf(productType).toString());
		
		Response response = request.post(Entity.entity(product,MediaType.APPLICATION_JSON),Response.class);
		
		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    product = response.readEntity(Product.class);
	    	if(null != product){
	    		ProductDTO productDTO = new ProductDTO(product);
	    		setBase64Image(productDTO);
	    		req.setAttribute("product", productDTO);
	    	}
		}
		req.getRequestDispatcher("product-details.jsp").forward(req, res);
	}

	private void setBase64Image(ProductDTO product) throws IOException {
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
			List<CategoryDTO> categories = new ArrayList<CategoryDTO>();
		    System.out.println("Success! " + response.getStatus());
		    categories = response.readEntity(CategoriesApiResponse.class).getCategories();
		    req.setAttribute("categories", categories);
		} else {
			ProductUtils.setServletError(req, response);
		}
	}
	
	private void getProductById(HttpServletRequest req) throws IOException {
		String productId = req.getParameter("productId");
		if(StringUtils.isEmpty(productId)) {
			productId = "0";
		}
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.GET_PRODUCT_BY_ID_API_URL.replace(ApiConstants.PRODUCT_ID_REPLACE, productId));
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
	    	Product product = response.readEntity(Product.class);
	    	if(null != product){
	    		ProductDTO productDTO = new ProductDTO(product);
	    		setBase64Image(productDTO);
	    		req.setAttribute("product", productDTO);
	    	}
		} else {
			ProductUtils.setServletError(req, response);
		}
	}
	
	private void getAllProducts(HttpServletRequest req) throws IOException{
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.GET_ALL_PRODUCTS_API_URL);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    ProductsApiResponse apiResponse = new ProductsApiResponse();
		    apiResponse = response.readEntity(new GenericType<ProductsApiResponse>() {});
	    	//get categories to set in the filters
	    	getCategories(req);
	    	for(ProductDTO product : apiResponse.getProducts()) {
	    		setBase64Image(product);
	    	}
	    	req.setAttribute("products", apiResponse.getProducts());
		} else {
			ProductUtils.setServletError(req, response);
		}
	}

}
