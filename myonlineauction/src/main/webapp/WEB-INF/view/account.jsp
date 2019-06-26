<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>Tiny Auction</title>

<link
	href="https://fonts.googleapis.com/css?family=Lato:300,400,400i,700,700i"
	rel="stylesheet">
<link href="https://unpkg.com/ionicons@4.5.5/dist/css/ionicons.min.css"
	rel="stylesheet">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/grid.css" />" rel="stylesheet">
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	<section class="account-rows">
		<h1>Bidding</h1>
			<c:forEach items="${bids }" var="bid">
			
			<div class="row account-row">
				<div class = "col span-3-of-5">
				<a href = "${ pageContext.request.contextPath}/auction/all/${bid.listingId}">${bid.listingName}</a></div>
				<div class = "col span-1-of-5">${bid.price }</div>
				<c:if test="${bid.outbid==true }">
					<div class = "col span-1-of-5">outbid</div>
				</c:if>
			
			</div>
			</c:forEach>
		
		<h1>Offers</h1>
		<c:forEach items="${offers }" var="offer">
			<div class="row account-row">
				<div class = "col span-3-of-5">
					<a href = "${ pageContext.request.contextPath}/auction/all/${offer.listingId}">${offer.listingName}</a></div>
				
				<div class = "col span-1-of-5">${offer.price }</div>
				<div class = "col span-1-of-5">${offer.numberOfBids }</div>
			</div>
			</c:forEach>
		<h1>Listings won</h1>
		<c:forEach items="${listings }" var="listing">
			<div class="row account-row">
				<div class = "col span-3-of-6">${listing.listingName }</div>
				<div class = "col span-1-of-6">${listing.price }</div>
				<div class = "col span-2-of-6">${listing.date }</div>
			</div>
			</c:forEach>
	</section>
	
	<jsp:include page="footer.jsp"></jsp:include>
</body>


</html>



