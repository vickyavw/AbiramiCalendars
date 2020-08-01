<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<html>
    <head>
        <title>Abirami Traders</title>
    </head>
    <body>
   		<c:forEach items="${items}" var="item">  
    	<table>
  			<tr> <td colspan="2"><img src="data:image/jpg;base64,${item.base64Image}" width="240" height="300"/></td> </tr>
  			<tr> <td>Item Id </td><td> <a href="user?itemId=${item.itemId}">${item.itemId}</a></td> </tr>
    		<tr> <td>Item Name </td><td> ${item.displayName}</td> </tr>
    		<tr> <td>Item Description </td><td> ${item.description}</td> </tr>
		</table>    		
		</c:forEach> 
		<c:if test="${empty items}">
			<h2>Invalid Item Id</h2>
		</c:if>
    </body>
</html>