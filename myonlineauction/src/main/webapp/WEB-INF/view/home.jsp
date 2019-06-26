<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Tiny Auction</title>
        <link href="https://fonts.googleapis.com/css?family=Lato:300,400,400i,700,700i" rel="stylesheet">
        <link href="https://unpkg.com/ionicons@4.5.5/dist/css/ionicons.min.css" rel="stylesheet">
        <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/grid.css" />" rel="stylesheet">
    	
    </head>
    
    <body>
    	<jsp:include page="header.jsp"></jsp:include>
    	 
        <section class="hot-categories">
            <div class="row">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/auction/New-Today">New Today</a></li><li><a href="${pageContext.request.contextPath}/auction/Ending-Today">Ending Today</a></li><li><a href="${pageContext.request.contextPath}/auction/Hot-50">Hot 50</a></li><li><a href="${pageContext.request.contextPath}/auction/Last-Chance">Last Chance</a></li></ul>
            </div>
            
        </section>
        <section>
            <div class="row">
                <h1>Categories</h1>
            </div>
            
            <div>
             <ul class="primary-categories clearfix">
                <li><a href="${pageContext.request.contextPath}/auction/Electronics"><img alt="image"  src="<c:url value="resources/images/electronics.jpg" />"><h2>Electronics</h2></a>
                 </li>
                 <li><a href="${pageContext.request.contextPath}/auction/Fashion"><img src="<c:url value="resources/images/fasion.jpg" />"><h2>Fashion</h2></a>
                 </li>
                 <li><a href="${pageContext.request.contextPath}/auction/Health%20and%20Beauty"><img src="<c:url value="resources/images/health_and_beauty.jpg" />"><h2>Health and Beauty</h2></a>
                 </li>
                 <li><a href="${pageContext.request.contextPath}/auction/Home%20and%20Garden"><img src="<c:url value="resources/images/home_and_garden.jpg" />"><h2>Home and Garden</h2></a>
                 </li>
            </ul>
            <ul class="primary-categories clearfix">
                <li><a href="${pageContext.request.contextPath}/auction/Sporting%20Goods"><img src="<c:url value="resources/images/sporting_goods.jpg" />"><h2>Sporting Goods</h2></a>
                 </li>
                 <li><a href="${pageContext.request.contextPath}/auction/Collectibles%20and%20Art"><img src="<c:url value="resources/images/colectibles_and_art.jpg" />"><h2>Collectibles and Art</h2></a>
                 </li>
                 <li><a href="${pageContext.request.contextPath}/auction/Music%20and%20Books"><img src="<c:url value="resources/images/music_and_books.jpg" />"><h2>Music and Books</h2></a>
                 </li>
                 <li><a href="${pageContext.request.contextPath}/auction/Toys"><img src="<c:url value="resources/images/toys.jpg" />"><h2>Toys</h2></a>
                 </li>
            </ul>
            
            </div>
            
        </section>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>


</html>