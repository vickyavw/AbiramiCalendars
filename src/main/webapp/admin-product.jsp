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
		<a href="admin-product?getAll=true">All Products</a>
		<br/><br/>
		<input name="productId" type="text" maxlength="512" id="productId"/>
		<a id="submit" onclick="getProduct()" href="">Get Product Details</a>
		<br/><br/>
		<h2>Add Product</h2>
		<form action = "admin-product" enctype="multipart/form-data" method = "POST">
			<table>
				<tr><td>Product Name </td><td><input name="productName" type="text" maxlength="512" id="productName"/></td></tr>
				<tr><td>Product Desc </td><td><input name="productDesc" type="text" maxlength="512" id="productDesc"/></td></tr>
				<tr><td>Product Type </td>
					<td>	
						<select name="productType" id="productType">
						    <c:forEach items="${productTypes}" var="productType">
						        <option value="${productType}">${productType}</option>
						    </c:forEach>
						</select>
					</td>
				</tr>	
				<tr><td>Format </td>
					<td>	
						<select name="formatId" id="formatId">
						    <c:forEach items="${formats}" var="format">
						        <option value="${format.formatId}">${format.formatName}</option>
						    </c:forEach>
						</select>
					</td>
				</tr>			
				<tr><td>Category </td>
					<td>
					<select name="categoryId" id="categoryId">
					    <c:forEach items="${categories}" var="category">
					        <option value="${category.categoryId}">${category.categoryName}</option>
					    </c:forEach>
					</select>
					</td>
				</tr>
				<tr><td>Price </td><td><input type="text" name="price" /></td></tr>
				<tr><td>Image </td><td><input type="file" name="file" /></td></tr>
				<tr><td colspan="2" align="center"><input type="submit" value="Add Product"/></td></tr>
			</table>
		</form>
	</body>
</html>