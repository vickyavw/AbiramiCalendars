<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<html>
	<head>
	    <title>Abirami Traders</title>
	</head>
	<script>
			function getProduct(){
				document.getElementById('submit').href = "admin-product?productId="+document.getElementById('productId').value;
			}
			
			function changeFormat(value){
		        var children = document.getElementById("formatContainer").children;
		        for (var i = 1, len = children.length ; i < len; i++) {
		        	if(children[i].id === value){
		            	children[i].style.display = "block";
		            	children[i].children[0].required = true;
		        	}
		        	else {
		        		children[i].style.display = "none";
		        		children[i].children[0].required = false;
		        	}
		        }
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
				<tr><td>Product Name </td><td><input name="productName" type="text" maxlength="512" id="productName" required="required"/></td></tr>
				<tr><td>Product Desc </td><td><input name="productDesc" type="text" maxlength="512" id="productDesc" required="required"/></td></tr>
				<tr><td>Product Type </td>
					<td>	
						<select name="productType" id="productType" onChange="changeFormat(this.value);">
						    <c:forEach items="${productTypes}" var="productType">
						        <option value="${productType}">${productType}</option>
						    </c:forEach>
						</select>
					</td>
				</tr>	
				<tr id ="formatContainer"><td>Format </td>
					<td id="DEFAULT" style="display:block;">	
						<select name="formatId" id="formatId" required="required">
							<option value="" disabled selected>Select</option>
						</select>
					</td>
					<td id="CALENDAR" style="display:none;">	
						<select name="CALENDARFormatId" id="CALENDARFormatId" required="required">
						    <c:forEach items="${formats}" var="format">
						    	<c:if test="${format.productType eq 'CALENDAR'}">	
						        	<option value="${format.formatId}">${format.formatName}</option>
						        </c:if>
						    </c:forEach>
						</select>
					</td>
					<td id="DIARY" style="display:none;">	
						<select name="DIARYFormatId" id="DIARYFormatId" required="required">
						    <c:forEach items="${formats}" var="format">
						    	<c:if test="${format.productType eq 'DIARY'}">	
						        	<option value="${format.formatId}">${format.formatName}</option>
						        </c:if>
						    </c:forEach>
						</select>
					</td>
					<td id="BOX" style="display:none;">	
						<select name="BOXFormatId" id="BOXFormatId" required="required">
						    <c:forEach items="${formats}" var="format">
						    	<c:if test="${format.productType eq 'BOX'}">	
						        	<option value="${format.formatId}">${format.formatName}</option>
						        </c:if>
						    </c:forEach>
						</select>
					</td>
					<td id="LABEL" style="display:none;">	
						<select name="LABELFormatId" id="LABELFormatId" required="required">
						    <c:forEach items="${formats}" var="format">
						    	<c:if test="${format.productType eq 'LABEL'}">	
						        	<option value="${format.formatId}">${format.formatName}</option>
						        </c:if>
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
				<tr><td>Price </td><td><input type="text" name="price" required="required"/></td></tr>
				<tr><td>Image </td><td><input type="file" name="file" required="required"/></td></tr>
				<tr><td colspan="2" align="center"><input type="submit" value="Add Product"/></td></tr>
			</table>
		</form>
	</body>
</html>