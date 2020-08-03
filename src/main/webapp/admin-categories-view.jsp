<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<html>
    <head>
        <title>Abirami Traders</title>
    </head>
    <body>
    	<c:choose>
	    	<c:when test="${not empty categories}">
		   		<c:forEach items="${categories}" var="category">
	    		<table>
		  				<tr> <td>Category Id </td><td> ${category.categoryId}</td> </tr>
		    			<tr> <td>Category Name </td><td> ${category.displayName}</td> </tr>
		    			<tr> <td>Category Description </td><td> ${category.description}</td> </tr>
		    			<%-- <c:when test="${not empty category.products}">
		    				<tr> <td>List of Products in the category </td><td></td> </tr>
		    				<c:forEach items="${products}" var="product">
		    					<tr> <td>Product Id </td><td> ${product.productId}</td> </tr>
		    					<tr> <td>Product Name </td><td> ${product.displayName}</td> </tr>
		    				</c:forEach>
		    			</c:when> --%>
				</table>  
		   		<br/><br/>
				</c:forEach>
			</c:when> 
			<c:when test="${not empty errorCode}">
				<h2>${errorCode} : ${errorDesc}</h2>
			</c:when>
			<c:when test="${empty errorCode}">
				<h2>Invalid Product Id</h2>
			</c:when>
		</c:choose>
    </body>
</html>