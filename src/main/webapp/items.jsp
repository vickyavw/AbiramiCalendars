<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<html>
    <head>
        <title>Abirami Traders</title>
    </head>
    <body>
    	<c:choose>
	    	<c:when test="${not empty items}">
	    		<table>
		   		<c:forEach items="${items}" var="item" varStatus="rowCounter">
		  			<c:if test="${rowCounter.count % 5 == 1}">
		  				<tr>
    				</c:if>
		  			<td style="padding:50px"><a href="user?itemId=${item.itemId}"><img src="data:image/jpg;base64,${item.base64Image}" width="240" height="300" title="${item.displayName}"/></a></td> 
		  			 <c:if test="${rowCounter.count % 5 == 0||rowCounter.count == fn:length(values)}">
		  			 	</tr>
		  			 </c:if>	
				</c:forEach>
				</table>  
			</c:when> 
			<c:when test="${not empty item}">
		   		<table>
		  			<tr> <td colspan="2"><img src="data:image/jpg;base64,${item.base64Image}" width="240" height="300"/></td> 
		  			</tr>
		  			<tr> <td>Item Id </td><td> ${item.itemId}</a></td> </tr>
		    		<tr> <td>Item Name </td><td> ${item.displayName}</td> </tr>
		    		<tr> <td>Item Description </td><td> ${item.description}</td> </tr>
				</table>
			</c:when> 
			<c:when test="${not empty errorCode}">
				<h2>${errorCode} : ${errorDesc}</h2>
			</c:when>
			<c:when test="${empty errorCode}">
				<h2>Invalid Item Id</h2>
			</c:when>
		</c:choose>
    </body>
</html>