package com.abirami.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;

import com.abirami.model.PaginatedProductsApiResponse;
import com.abirami.model.ProductDTO;
import com.abirami.util.ApiConstants;
import com.abirami.util.ProductUtils;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home" }, loadOnStartup = 1) 
public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = -859109638477565471L;
	
	public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		getLatestProducts(req);
		req.getRequestDispatcher("home.jsp").forward(req, res);
		return;
	
	}

	private void getLatestProducts(HttpServletRequest req) throws IOException {

		//fetch latest 8 products
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(ApiConstants.GET_PRODUCTS_BY_QUERY_API_URL)
										.queryParam(ApiConstants.SORT_BY_QUERY_PARAM, "id")
										.queryParam(ApiConstants.SORT_DIRECTION_QUERY_PARAM, "desc")
										.queryParam(ApiConstants.PAGE_SIZE_QUERY_PARAM, 8)
										.queryParam(ApiConstants.PAGE_NUMBER_QUERY_PARAM, 1);
		
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
		    req.setAttribute("products", apiResponse.getProducts());
		} else {
			ProductUtils.setServletError(req, response);
		}
		
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

}
