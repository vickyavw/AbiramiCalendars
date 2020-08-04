<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<html>
	<head>
	    <title>Abirami Traders</title>
	</head>
	<script>
			function getProduct(){
				document.getElementById('submit').href = "admin-product?productId="+document.getElementById('productId').value;
			}
	</script>
	<body>
	
		<h2>REST Webservice APIs</h2>
		<a href="product">All Products</a>
		<br/><br/>
		<input name="productId" type="text" maxlength="512" id="productId"/>
		<a id="submit" onclick="getProduct()" href="">Get Product Details</a>
		<br/><br/>
		<h2>Add Product</h2>
		<form action = "admin-product" enctype="multipart/form-data" method = "POST">
			Product Name:<input name="productName" type="text" maxlength="512" id="productName"/><br/>
			Product Desc:<input name="productDesc" type="text" maxlength="512" id="productDesc"/><br/>
			Category Id:
			<select name="categoryId" id="categoryId">
			    <c:forEach items="${categories}" var="category">
			        <option value="${category.categoryId}">${category.displayName}</option>
			    </c:forEach>
			</select>
			<br/>
			Image:<input type="file" name="file" /><br/>
			<input type="submit" value="Add Product"/>
		</form>
	</body>
</html>