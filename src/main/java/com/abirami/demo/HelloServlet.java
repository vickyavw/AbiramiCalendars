package com.abirami.demo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.apache.commons.lang3.StringUtils;

import com.abirami.model.Item;

@WebServlet(name = "UsersServlet", urlPatterns = {"user"}, loadOnStartup = 1) 
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String itemId = req.getParameter("itemId");
		String apiUrl = "http://localhost:8080/api/items";
		if(StringUtils.isNoneEmpty(itemId))
			apiUrl = apiUrl+"/"+itemId;
		Client client = ClientBuilder.newClient();
		WebTarget resource = client.target(apiUrl);
		
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		Response response = request.get();

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
		    System.out.println("Success! " + response.getStatus());
		    if(StringUtils.isNoneEmpty(itemId)) {
		    	Item item = response.readEntity(Item.class);
		    	if(null != item){
		    		byte[] img = item.getImage();
		    		if(img == null) {
		    			res.getWriter().print("Image not available");
		    		}
		    		else {
		    			ByteArrayInputStream bis = new ByteArrayInputStream(img);
		       			BufferedImage bImage = ImageIO.read(bis);
		        		ImageIO.write(bImage, "PNG", res.getOutputStream());
		        	}
		        }
		        else {
		    		res.getWriter().print("Invalid itemId");
		    	}
		    }
		    else {
		    	res.getWriter().print(response.readEntity(List.class));
		    }
		} else {
		    System.out.println("ERROR! " + response.getStatus());    
		    System.out.println(response.getEntity());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
        if (name == null) {
        	name = "World";
        }
        request.setAttribute("user", name);
        request.getRequestDispatcher("response.jsp").forward(request, response);
	}

}
