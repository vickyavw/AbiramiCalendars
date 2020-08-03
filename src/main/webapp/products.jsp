<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<html>
    <head>
        <title>Abirami Traders</title>
    </head>
    <body>
    	<c:choose>
	    	<c:when test="${not empty products}">
	    		<table>
		   		<c:forEach items="${products}" var="product" varStatus="rowCounter">
		  			<c:if test="${rowCounter.count % 5 == 1}">
		  				<tr>
    				</c:if>
		  			<td style="padding:50px"><a href="user?productId=${product.productId}"><img src="data:image/jpg;base64,${product.base64Image}" width="240" height="300" title="${product.displayName}"/></a></td> 
		  			 <c:if test="${rowCounter.count % 5 == 0||rowCounter.count == fn:length(values)}">
		  			 	</tr>
		  			 </c:if>	
				</c:forEach>
				</table>  
			</c:when> 
			<c:when test="${not empty product}">
		   		<table>
		  			<tr> <td colspan="2"><img src="data:image/jpg;base64,${product.base64Image}" width="240" height="300"/></td> 
		  			</tr>
		  			<tr> <td>Product Id </td><td> ${product.productId}</a></td> </tr>
		    		<tr> <td>Product Name </td><td> ${product.displayName}</td> </tr>
		    		<tr> <td>Product Description </td><td> ${product.description}</td> </tr>
				</table>
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