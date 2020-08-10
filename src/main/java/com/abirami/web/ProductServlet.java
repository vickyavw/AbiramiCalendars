package com.abirami.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import com.abirami.model.PaginatedProductsApiResponse;
import com.abirami.model.ProductDTO;
import com.abirami.util.ApiConstants;
import com.abirami.util.ProductType;
import com.abirami.util.ProductUtils;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;

@WebServlet(name = "ProductServlet", urlPatterns = {"/products", "/calendars", "/diaries", "/boxes", "/labels" }, loadOnStartup = 1) 
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Map<String, String> URL_PRODUCT_TYPE_MAP = new HashMap<String, String>();
	static {
		URL_PRODUCT_TYPE_MAP.put("Calendars", ProductType.CALENDAR.toString());
		URL_PRODUCT_TYPE_MAP.put("Diaries", ProductType.DIARY.toString());
		URL_PRODUCT_TYPE_MAP.put("Boxes", ProductType.BOX.toString());
		URL_PRODUCT_TYPE_MAP.put("Labels", ProductType.LABEL.toString());
	}
       
    public ProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String productType = req.getParameter(ApiConstants.PRODUCT_TYPE_QUERY_PARAM);
		productType = setDefaultValuesInRequest(req, productType);
		populateFormatCategoryMapInSession(req, productType);
		//view all products
		if(null == req.getQueryString()) {
			getAllProducts(req, productType);
			req.getRequestDispatcher("products.jsp").forward(req, res);
			return;
		
		}
		//to view individual product. 
		else if(null != req.getParameter("productId")) {
			getProductById(req, productType);
			req.getRequestDispatcher("product-details.jsp").forward(req, res);
    		return;
		}
		//if any queryParam is passed. Ideally productId shouldn't be called with any other criteria
		else {
			getProductsByCriteria(req, productType);
			//getCalendarsByCategory(req);
			req.getRequestDispatcher("products.jsp").forward(req, res);
			return;
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
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
	
	private void getProductsByCriteria(HttpServletRequest req, String productType) throws IOException {

		String pageNumber = req.getParameter(ApiConstants.PAGE_NUMBER_QUERY_PARAM);
		if(StringUtils.isBlank(pageNumber) || !StringUtils.isNumeric(pageNumber))
			pageNumber = "1";
		String priceMin = req.getParameter("priceMin");
		String priceMax = req.getParameter("priceMax");
		//setting to show the values selected after loading the screen
		req.setAttribute("priceMinSel", priceMin);
		req.setAttribute("priceMaxSel", priceMax);
		
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.GET_PRODUCTS_BY_QUERY_API_URL)
										.queryParam(ApiConstants.PRODUCT_TYPE_QUERY_PARAM, URL_PRODUCT_TYPE_MAP.get(productType))
										.queryParam(ApiConstants.API_QUERY_PARAMS, req.getQueryString())
										.queryParam(ApiConstants.SORT_BY_QUERY_PARAM, req.getAttribute(ApiConstants.SORT_BY_QUERY_PARAM))
										.queryParam(ApiConstants.SORT_DIRECTION_QUERY_PARAM, req.getAttribute(ApiConstants.SORT_DIRECTION_QUERY_PARAM))
										.queryParam(ApiConstants.PAGE_SIZE_QUERY_PARAM, req.getAttribute(ApiConstants.PAGE_SIZE_QUERY_PARAM))
										.queryParam(ApiConstants.PAGE_NUMBER_QUERY_PARAM, pageNumber);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);
		
		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    PaginatedProductsApiResponse apiResponse = new PaginatedProductsApiResponse();
		    apiResponse = response.readEntity(new GenericType<PaginatedProductsApiResponse>() {});
	    	for(ProductDTO product : apiResponse.getProducts()) {
	    		setBase64Image(product);
	    	}
	    	setReqAttrFromApiResponse(req, apiResponse);
		} else {
			ProductUtils.setServletError(req, response);
		}
		
	}
	
	private void getProductById(HttpServletRequest req, String productType) throws IOException {
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
					.queryParam(ApiConstants.PRODUCT_TYPE_QUERY_PARAM, URL_PRODUCT_TYPE_MAP.get(productType));
		
			Builder request = resource.request();
			request.accept(MediaType.APPLICATION_JSON);
	
			Response response = request.get();
	
			if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
			    System.out.println("Success! " + response.getStatus());
			    ProductDTO product = response.readEntity(ProductDTO.class);
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
			getRelatedProducts(req, productId, productType, relatedProductsCount);
		}
	}
	
	private void getRelatedProducts(HttpServletRequest req, String productId, String productType, int relatedProductsCount) throws IOException {
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.GET_RELATED_PRODUCTS_OF_ID_URL.replace(ApiConstants.PRODUCT_ID_REPLACE, productId))
										.queryParam(ApiConstants.PRODUCT_TYPE_QUERY_PARAM, URL_PRODUCT_TYPE_MAP.get(productType))
										.queryParam(ApiConstants.ROWS_TO_FETCH, relatedProductsCount);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    List<ProductDTO> responseMap = response.readEntity(new GenericType<List<ProductDTO>>() {});
		    ProductDTO product = responseMap.get(0);
		    if(null != product){
	    		setBase64Image(product);
	    		req.setAttribute("product", product);
	    	}
		    responseMap.remove(0);
	    	for(ProductDTO relatedProduct : responseMap) {
	    		setBase64Image(relatedProduct);
	    	}
		    req.setAttribute("relatedProducts", responseMap);
		} else {
			ProductUtils.setServletError(req, response);
		}
		
	}

	private void getAllProducts(HttpServletRequest req, String productType) throws IOException{

		String pageNumber = req.getParameter(ApiConstants.PAGE_NUMBER_QUERY_PARAM);
		if(StringUtils.isBlank(pageNumber) || !StringUtils.isNumeric(pageNumber))
			pageNumber = "1";
		
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.GET_ALL_PRODUCTS_API_URL)
										.queryParam(ApiConstants.PRODUCT_TYPE_QUERY_PARAM, URL_PRODUCT_TYPE_MAP.get(productType))
										.queryParam(ApiConstants.SORT_BY_QUERY_PARAM, req.getAttribute(ApiConstants.SORT_BY_QUERY_PARAM))
										.queryParam(ApiConstants.SORT_DIRECTION_QUERY_PARAM, req.getAttribute(ApiConstants.SORT_DIRECTION_QUERY_PARAM))
										.queryParam(ApiConstants.PAGE_SIZE_QUERY_PARAM, req.getAttribute(ApiConstants.PAGE_SIZE_QUERY_PARAM))
										.queryParam(ApiConstants.PAGE_NUMBER_QUERY_PARAM, pageNumber);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    PaginatedProductsApiResponse apiResponse = new PaginatedProductsApiResponse();
		    apiResponse = response.readEntity(new GenericType<PaginatedProductsApiResponse>() {});
	    	for(ProductDTO product : apiResponse.getProducts()) {
	    		setBase64Image(product);
	    	}
	    	setReqAttrFromApiResponse(req, apiResponse);
		} else {
			ProductUtils.setServletError(req, response);
		}
	}

	private void populateFormatCategoryMapInSession(HttpServletRequest req, String productType) throws IOException {
		/*
		 * FormatCategoryMap will be static for each Product type.
		 * Hence saving in the session for each product type when loading each product.
		 * session attributes will be named like - CalendarFormatCategoryMap, DiaryFormatCategoryMap, etc.
		 */
		String currentProductMap = productType + "FormatCategoryMap";
		if(req.getSession().getAttribute(currentProductMap) == null) {
			getAllProducts(req, productType);
			List<ProductDTO> list = (List<ProductDTO>) req.getAttribute("products");
			Map<String, List<String>> formatCategoryMap = new HashMap<String, List<String>>();
			for(ProductDTO product : list) {
				List<String> categories;
				if(null == formatCategoryMap.get(product.getFormatName())) {
					categories = new ArrayList<String>();
				}
				else {
					categories = formatCategoryMap.get(product.getFormatName());
				}
				if(!categories.contains(product.getCategoryName()))
					categories.add(product.getCategoryName());
				formatCategoryMap.put(product.getFormatName(), categories);
			}
		    //req.setAttribute("formatCategoryMap", formatCategoryMap);
		    req.getSession().setAttribute(currentProductMap, formatCategoryMap);
		}
	}

	private void setReqAttrFromApiResponse(HttpServletRequest req, PaginatedProductsApiResponse apiResponse) {
		String queryParams = req.getQueryString();
		/* resetting priceFilter and pageNumbers for next calls
		 * Expected values - Can either have existing filter or only pageNumbers. 
		 * So have to add existing filter if any
		 * - categoryId=1&xyz=abc&pageNumber=2
		 * - categoryId=2
		 * - pageNumber=2
		 * - <empty>
		 */
		if(null == queryParams)
			queryParams = "";
		else if(queryParams.contains(ApiConstants.PAGE_NUMBER_QUERY_PARAM))
			queryParams = queryParams.substring(0, req.getQueryString().indexOf(ApiConstants.PAGE_NUMBER_QUERY_PARAM));
		else
			queryParams = queryParams+"&";
		req.setAttribute("existingFilter", queryParams);
		req.setAttribute("products", apiResponse.getProducts());
		req.setAttribute(ApiConstants.PAGE_NUMBER_QUERY_PARAM, apiResponse.getPageNumber());
		req.setAttribute(ApiConstants.TOTAL_PAGES_QUERY_PARAM, ((apiResponse.getResultSize() - 1) / apiResponse.getPageSize()) +1 );
	}

	private String setDefaultValuesInRequest(HttpServletRequest req, String currentProduct) {
		//set default values in request
		if(StringUtils.isNotBlank(currentProduct)) {
			if(null == URL_PRODUCT_TYPE_MAP.get(currentProduct)) {
				//URL can have productType enum value directly if navigated from home page or products page as we populate based on value from db, so it will be CALENDAR, DIARY
				currentProduct = URL_PRODUCT_TYPE_MAP.entrySet().stream()
										.filter(e -> e.getValue().equals(req.getParameter(ApiConstants.PRODUCT_TYPE_QUERY_PARAM).toUpperCase()))
										.map(Entry::getKey)
										.findFirst()
										.get();
			}
		}
		req.setAttribute("currentProduct", currentProduct);
		req.setAttribute("priceMin", "1");
		req.setAttribute("priceMax", "99");
		req.setAttribute("relatedProducts", null);
		req.setAttribute("products", null);
		req.setAttribute("product", null);
		return currentProduct;
	}

}
