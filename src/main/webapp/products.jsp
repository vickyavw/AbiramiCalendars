<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<html lang="zxx">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="Ashion Template">
    <meta name="keywords" content="Ashion, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Abirami Traders</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Cookie&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700;800;900&display=swap"
    rel="stylesheet">

    <!-- Css Styles -->
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="css/jquery-ui.min.css" type="text/css">
    <link rel="stylesheet" href="css/magnific-popup.css" type="text/css">
    <link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="css/style.css" type="text/css">
    
</head>

<body>
    <!-- Page Preloder -->
    <div id="preloder">
        <div class="loader"></div>
    </div>

    <!-- Offcanvas Menu Begin -->
    <div class="offcanvas-menu-overlay"></div>
    <div class="offcanvas-menu-wrapper">
        <div class="offcanvas__close">+</div>
        <ul class="offcanvas__widget">
            <li><span class="icon_search search-switch"></span></li>
            <li><a href="#"><span class="icon_heart_alt"></span>
                <div class="tip">2</div>
            </a></li>
            <li><a href="#"><span class="icon_bag_alt"></span>
                <div class="tip">2</div>
            </a></li>
        </ul>
        <div class="offcanvas__logo">
            <a href="/"><img src="images/logo.png" alt=""></a>
        </div>
        <div id="mobile-menu-wrap"></div>
        <div class="offcanvas__auth">
            <a href="#">Login</a>
            <a href="#">Register</a>
        </div>
    </div>
    <!-- Offcanvas Menu End -->
	
	  <script type="text/javascript">
	   	function call_func(){
	   		debugger;
	   		var uri = '${currentProductUri}' +'?'+ '${existingFilter}';
	   		var priceMin = document.getElementById('minamount').value.substr(3);
	   		var priceMax = document.getElementById('maxamount').value.substr(3);
	   		uri = updateQueryStringParameter(uri, 'priceMin', priceMin);
	   		uri = updateQueryStringParameter(uri, 'priceMax', priceMax);
	   		window.location.href = uri;
	   	}
	   	function updateQueryStringParameter(uri, key, value) {
	   		var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
	   		var separator = uri.indexOf('?') !== -1 ? "&" : "?";
	   		if (uri.match(re)) {
	   			return uri.replace(re, '$1' + key + "=" + value + '$2');
	    	}
	    	else {
	    	  	return uri + separator + key + "=" + value;
	    	}
	   	}
	   </script>
    <!-- Header Section Begin -->
    <header class="header">
        <div class="container-fluid">
		    <div class="row">
		        <div class="col-xl-3 col-lg-2">
		            <div class="header__logo">
		                <a href="/"><img src="images/logo.png" alt=""></a>
		            </div>
		        </div>
		        <div class="col-xl-6 col-lg-7">
		            <nav class="header__menu">
		                <ul>
		                   <!-- <li class="active"><a href="./index.jsp">Home</a></li> -->
		                    <li class="${currentProduct == 'Calendars'? 'active' : ''}"><a href="/calendars">Calendars</a></li>
		                    <li class="${currentProduct == 'Diaries'? 'active' : ''}"><a href="/diaries">Diaries</a></li>
		                    <li class="${currentProduct == 'Boxes'? 'active' : ''}"><a href="/boxes">Boxes</a></li>
		                    <li class="${currentProduct == 'Labels'? 'active' : ''}"><a href="/labels">Labels</a>
		                        <ul class="dropdown">
		                            <li><a href="./product-details.html">Product Details</a></li>
		                            <li><a href="/product">Shop Cart</a></li>
		                            <li><a href="./checkout.html">Checkout</a></li>
		                            <li><a href="./blog-details.html">Blog Details</a></li>
		                            <li><a href="./admin-category.html">Admin Categories</a></li>
									<li><a href="/admin-product">Admin Products</a></li>
		                        </ul>
		                    </li>
		                    <li class="${currentProduct == 'Customize'? 'active' : ''}"><a href="/customize">Customize</a></li>
		                    <li class="${currentProduct == 'Contact'? 'active' : ''}"><a href="/contact">Contact</a></li>
		                </ul>
		            </nav>
		        </div>
		        <div class="col-lg-3">
		            <div class="header__right">
		                <div class="header__right__auth">
		                    <a href="#">Login</a>
		                    <a href="#">Register</a>
		                </div>
		                <ul class="header__right__widget">
		                    <li><span class="icon_search search-switch"></span></li>
		                    <li><a href="#"><span class="icon_heart_alt"></span>
		                        <div class="tip">2</div>
		                    </a></li>
		                    <li><a href="#"><span class="icon_bag_alt"></span>
		                        <div class="tip">2</div>
		                    </a></li>
		                </ul>
		            </div>
		        </div>
		    </div>
		    <div class="canvas__open">
		        <i class="fa fa-bars"></i>
		    </div>
		</div>
    </header>
    <!-- Header Section End -->

    <!-- Breadcrumb Begin -->
    <div class="breadcrumb-option">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="breadcrumb__links">
                        <a href="/"><i class="fa fa-home"></i> Home</a>
                        <span>${currentProduct}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Breadcrumb End -->

    <!-- Shop Section Begin -->
    <section class="shop spad">
        <div class="container">
            <div class="row">
                <div class="col-lg-3 col-md-3">
                    <div class="shop__sidebar">
                        <div class="sidebar__categories">
                            <div class="section-title">
                                <h4>Categories</h4>
                            </div>
                            <div class="categories__accordion">
                                <div class="accordion" id="accordionExample">
                                    <div class="card">
                                        <div class="card-heading active">
                                            <a data-toggle="collapse" data-target="#collapseOne">Daily Calendars</a>
                                        </div>
                                        <div id="collapseOne" class="collapse show" data-parent="#accordionExample">
                                            <div class="card-body">
                                                <ul>
                                                	<li class="active"><a href="${currentProductUri}">All</a></li>
                                                    <c:forEach items="${categories}" var="category">
							                        	<li><a href="${currentProductUri}?categoryId=${category.categoryId}">${category.categoryName}</a></li>
								                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card">
                                        <div class="card-heading">
                                            <a data-toggle="collapse" data-target="#collapseTwo">Monthly Calendars</a>
                                        </div>
                                        <div id="collapseTwo" class="collapse" data-parent="#accordionExample">
                                            <div class="card-body">
                                                <ul>
                                                	<li><a href="#">All</a></li>
                                                    <li><a href="#">Religion</a></li>
                                                    <li><a href="#">Leaders</a></li>
                                                    <li><a href="#">Movie Stars</a></li>
                                                    <li><a href="#">Pets</a></li>
                                                    <li><a href="#">Scenaries</a></li>
                                                    <li><a href="#">Babies</a></li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card">
                                        <div class="card-heading">
                                            <a data-toggle="collapse" data-target="#collapseThree">Desk Calendars</a>
                                        </div>
                                        <div id="collapseThree" class="collapse" data-parent="#accordionExample">
                                            <div class="card-body">
                                                <ul>
                                                	<li><a href="#">All</a></li>
                                                    <li><a href="#">Religion</a></li>
                                                    <li><a href="#">Leaders</a></li>
                                                    <li><a href="#">Movie Stars</a></li>
                                                    <li><a href="#">Pets</a></li>
                                                    <li><a href="#">Scenaries</a></li>
                                                    <li><a href="#">Babies</a></li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="sidebar__categories">
                        	<div class="section-title">
                                <h4>Sort By</h4>
                            </div>
                            <div class="sortby__filter__option">
                                <a href="#" > Price: High to Low </a>
                                <a href="#" > Price: Low to High </a>
                                <a href="#" > Name: Ascending (A to Z)</a>
                                <a href="#" > Name: Descending (Z to A)</a>
                                <a href="#" > Newest First </a>
                                <a href="#" > Discount </a>
                            </div>
	                	</div>
                        <div class="sidebar__filter">
                            <div class="section-title">
                                <h4>Shop by price</h4>
                            </div>
                            <div class="filter-range-wrap">
                                <div class="price-range ui-slider ui-corner-all ui-slider-horizontal ui-widget ui-widget-content"
                                data-min="${priceMin}" data-max="${priceMax}"></div>
                                <div class="range-slider">
                                    <div class="price-input">
                                        <p>Price:</p>
                                        <input type="text" id="minamount" value="${priceMinSel}">
                                        <input type="text" id="maxamount" value="${priceMaxSel}">
                                    </div>
                                </div>
                            </div>
                            <a href="javascript:call_func();">Filter</a>
                        </div>
                        <div class="sidebar__sizes">
                            <div class="section-title">
                                <h4>Shop by size</h4>
                            </div>
                            <div class="size__list">
                                <label for="xxs">
                                    xxs
                                    <input type="checkbox" id="xxs">
                                    <span class="checkmark"></span>
                                </label>
                                <label for="xs">
                                    xs
                                    <input type="checkbox" id="xs">
                                    <span class="checkmark"></span>
                                </label>
                                <label for="xss">
                                    xs-s
                                    <input type="checkbox" id="xss">
                                    <span class="checkmark"></span>
                                </label>
                                <label for="s">
                                    s
                                    <input type="checkbox" id="s">
                                    <span class="checkmark"></span>
                                </label>
                                <label for="m">
                                    m
                                    <input type="checkbox" id="m">
                                    <span class="checkmark"></span>
                                </label>
                                <label for="ml">
                                    m-l
                                    <input type="checkbox" id="ml">
                                    <span class="checkmark"></span>
                                </label>
                                <label for="l">
                                    l
                                    <input type="checkbox" id="l">
                                    <span class="checkmark"></span>
                                </label>
                                <label for="xl">
                                    xl
                                    <input type="checkbox" id="xl">
                                    <span class="checkmark"></span>
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-9 col-md-9">
                    <div class="row">
                    	<c:forEach items="${products}" var="product">
                        	<div class="col-lg-3 col-md-6">
                            	<div class="product__item" onclick="window.location='${currentProductUri}?productId=${product.productId}&getRelated=4'" style="cursor: pointer">
                            	
	                                <div class="product__item__pic set-bg" data-setbg="data:image/jpg;base64,${product.base64Image}">
		                                    <div class="label new">New</div>
		                                    <ul class="product__hover">
		                                        <li><a href="data:image/jpg;base64,${product.base64Image}" class="image-popup"><span class="arrow_expand"></span></a></li>
		                                        <li><a href="#"><span class="icon_heart_alt"></span></a></li>
		                                        <li><a href="#"><span class="icon_bag_alt"></span></a></li>
		                                    </ul>
	                                </div>
	                                <div class="product__item__text">
	                                    <h6>${product.productName}</h6>
	                                    <div class="product__price">Rs.${product.price}</div>
	                                </div>
	                            </div>
	                        </div>
	                    	</c:forEach>
                        <div class="col-lg-12 text-center">
                            <div class="pagination__option">
                            	<c:if test="${pageNumber != 1}">
                            		<a href="#"><i class="fa fa-angle-left"></i></a>
                            		<a href="${currentProductUri}?${existingFilter}pageNumber=${pageNumber - 1}">${pageNumber - 1}</a>
                            	</c:if>
                                <a>${pageNumber}</a>
                                <c:if test="${pageNumber lt noOfPages}">
                               	 	<a href="${currentProductUri}?${existingFilter}pageNumber=${pageNumber+1}">${pageNumber+1}</a>
	                                <a href="#"><i class="fa fa-angle-right"></i></a>
                               	</c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Shop Section End -->

    <!-- Footer Section Begin -->
    <footer class="footer">
        <div class="container">
            <div class="row">
                <div class="col-lg-4 col-md-6 col-sm-7">
                    <div class="footer__about">
                        <div class="footer__logo">
                            <a href="/"><img src="images/logo.png" alt=""></a>
                        </div>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt
                        cilisis.</p>
                        <div class="footer__payment">
                            <a href="#"><img src="images/payment/payment-1.png" alt=""></a>
                            <a href="#"><img src="images/payment/payment-2.png" alt=""></a>
                            <a href="#"><img src="images/payment/payment-3.png" alt=""></a>
                            <a href="#"><img src="images/payment/payment-4.png" alt=""></a>
                            <a href="#"><img src="images/payment/payment-5.png" alt=""></a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-2 col-md-3 col-sm-5">
                    <div class="footer__widget">
                        <h6>Quick links</h6>
                        <ul>
                            <li><a href="#">About</a></li>
                            <li><a href="#">Blogs</a></li>
                            <li><a href="#">Contact</a></li>
                            <li><a href="#">FAQ</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-2 col-md-3 col-sm-4">
                    <div class="footer__widget">
                        <h6>Account</h6>
                        <ul>
                            <li><a href="#">My Account</a></li>
                            <li><a href="#">Orders Tracking</a></li>
                            <li><a href="#">Checkout</a></li>
                            <li><a href="#">Wishlist</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-4 col-md-8 col-sm-8">
                    <div class="footer__newslatter">
                        <h6>NEWSLETTER</h6>
                        <form action="#">
                            <input type="text" placeholder="Email">
                            <button type="submit" class="site-btn">Subscribe</button>
                        </form>
                        <div class="footer__social">
                            <a href="#"><i class="fa fa-facebook"></i></a>
                            <a href="#"><i class="fa fa-twitter"></i></a>
                            <a href="#"><i class="fa fa-youtube-play"></i></a>
                            <a href="#"><i class="fa fa-instagram"></i></a>
                            <a href="#"><i class="fa fa-pinterest"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                    <div class="footer__copyright__text">
                        <p>Copyright &copy; <script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a></p>
                    </div>
                    <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                </div>
            </div>
        </div>
    </footer>
    <!-- Footer Section End -->

    <!-- Search Begin -->
    <div class="search-model">
        <div class="h-100 d-flex align-items-center justify-content-center">
            <div class="search-close-switch">+</div>
            <form class="search-model-form">
                <input type="text" id="search-input" placeholder="Search here.....">
            </form>
        </div>
    </div>
    <!-- Search End -->

    <!-- Js Plugins -->
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.magnific-popup.min.js"></script>
    <script src="js/jquery-ui.min.js"></script>
    <script src="js/mixitup.min.js"></script>
    <script src="js/jquery.countdown.min.js"></script>
    <script src="js/jquery.slicknav.js"></script>
    <script src="js/owl.carousel.min.js"></script>
    <script src="js/jquery.nicescroll.min.js"></script>
    <script src="js/main.js"></script>
</body>

</html>