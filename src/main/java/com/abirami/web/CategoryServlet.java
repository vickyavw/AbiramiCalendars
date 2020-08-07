package com.abirami.web;

import java.io.IOException;
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

import org.apache.commons.lang3.StringUtils;

import com.abirami.model.ApiError;
import com.abirami.model.CategoriesApiResponse;
import com.abirami.model.Category;
import com.abirami.model.CategoryDTO;
import com.abirami.model.ProductsApiResponse;
import com.abirami.util.ApiConstants;

@WebServlet(name = "CategoryServlet", urlPatterns = {"category"}, loadOnStartup = 1) 
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String categoryId = req.getParameter("categoryId");
		String apiUrl = ApiConstants.CATEGORIES_API_URL;
		if(null != categoryId) {
			if(StringUtils.isEmpty(categoryId)) {
				categoryId = "0";
			}
			apiUrl = apiUrl+"/"+categoryId;
		}
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(apiUrl);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    List<CategoryDTO> categories = new ArrayList<CategoryDTO>();
		    if(StringUtils.isNotEmpty(categoryId)) {
		    	CategoryDTO apiResponse = response.readEntity(new GenericType<CategoryDTO>() {});
		    	if(null != apiResponse){
		    		categories.add(apiResponse);
		    		getAllProductsByCategory(req);
		    	
		    	}
		    	req.setAttribute("categories", categories);
		    }
		    else {
		    	CategoriesApiResponse apiResponse = response.readEntity(new GenericType<CategoriesApiResponse>() {});
		    	req.setAttribute("categories", apiResponse.getCategories());
		    }
		} else {
			ApiError error = response.readEntity(ApiError.class);
		    System.out.println("ERROR! " + response.getStatus());    
		    System.out.println(response.getStatus() + " : " +response.getStatusInfo().getReasonPhrase());
		    req.setAttribute("errorCode", error.getErrorCode());
		    req.setAttribute("errorDesc", error.getErrorDescription());
		}
		req.getRequestDispatcher("admin-categories-view.jsp").forward(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String name = req.getParameter("categoryName");
		String desc = req.getParameter("categoryDesc");
		String apiUrl = ApiConstants.CATEGORIES_API_URL;
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(apiUrl);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);
		
		Category category = new Category();
		category.setDisplayName(name);
		category.setDescription(desc);
		
		Response response = request.post(Entity.entity(category,MediaType.APPLICATION_JSON),Response.class);
		
		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    category = response.readEntity(Category.class);
		    List<Category> categories = new ArrayList<Category>();
	    	if(null != category){
	    		categories.add(category);
	    	}
	    	req.setAttribute("categories", categories);
		}
		req.getRequestDispatcher("admin-categories-view.jsp").forward(req, res);
	}
	
	private void getAllProductsByCategory(HttpServletRequest req) throws IOException {
//		String categoryId = req.getParameter("categoryId");
//		if(null == categoryId || !StringUtils.isNumeric(categoryId)) {
//			categoryId = "1";
//		}
//		
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.GET_PRODUCTS_BY_QUERY_API_URL)
										.queryParam(ApiConstants.API_QUERY_PARAMS, req.getQueryString())
										.queryParam(ApiConstants.SORT_BY_QUERY_PARAM, req.getAttribute(ApiConstants.SORT_BY_QUERY_PARAM))
										.queryParam(ApiConstants.SORT_DIRECTION_QUERY_PARAM, req.getAttribute(ApiConstants.SORT_DIRECTION_QUERY_PARAM))
										.queryParam(ApiConstants.PAGE_SIZE_QUERY_PARAM, req.getAttribute(ApiConstants.PAGE_SIZE_QUERY_PARAM))
										.queryParam(ApiConstants.PAGE_NUMBER_QUERY_PARAM, req.getAttribute(ApiConstants.PAGE_NUMBER_QUERY_PARAM));
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);
		
		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    ProductsApiResponse apiResponse = new ProductsApiResponse();
		    apiResponse = response.readEntity(new GenericType<ProductsApiResponse>() {});
		    req.setAttribute("products", apiResponse.getProducts());
			req.setAttribute(ApiConstants.PAGE_NUMBER_QUERY_PARAM, apiResponse.getPageNumber());
			req.setAttribute(ApiConstants.TOTAL_PAGES_QUERY_PARAM, ((apiResponse.getResultSize() - 1) / apiResponse.getPageSize()) +1 );
		} 
		
	}

}
