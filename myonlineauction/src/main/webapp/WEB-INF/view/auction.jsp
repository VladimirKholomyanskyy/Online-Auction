<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>Auction</title>
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

	<c:if test="${successMessage!=null}">
		<div class="row">
			<div class= "success-message">${successMessage }</div>
		</div>

	</c:if>
	<c:if test="${errorMessage!=null}">
		<div class="row">
			<div class="error-message">${errorMessage }</div>
		</div>

	</c:if>
	
	<section class="item-section blue-shadow">

		<div class="row">
			<div class="col span-1-of-2">
				<figure class="item-image">
					<img src="${listing.imagePath }" alt="image">
				</figure>
			</div>
			
			<div class="col span-1-of-2">
				
					<div class="row">
					<h3 class="listing-header">${listing.name }</h3>
				</div>
				<div class="row">
					
						<input type="hidden" id="endTimeId" value="${listing.endTime}">
						<label>Time left:&nbsp;</label> <span id="timeLeftId"
							class="values">${listing.endTime}</span>

				

				</div>
				<div class="row">
					<div class="col span-1-of-2">
						<c:if test="${listing.numberOfBids!=0 }">
							<label>Current Bid:&nbsp;</label>
						</c:if>
						<c:if test="${listing.numberOfBids==0 }">
							<label>Starting Bid:&nbsp;</label>
						</c:if>
						<span class="values">${listing.currentBid }</span>

					</div>
					<div class="col span-1-of-2">
						<div class="values">${listing.numberOfBids }&nbsp;bids</div>
					</div>
				</div>
				<div class="row">
					<form:form
						action="${pageContext.request.contextPath }/auction/placeBid"
						method="post" modelAttribute="bid">
						<div class="row">
							
							<div class="col span-1-of-2">
								<div class="bid-field">
									<form:errors path="bid" cssClass="error"/>
									<form:input path="bid" />
								</div>

							</div>

							<div class="col span-1-of-2">
								<input class="place-bid" type="submit" value="Place bid">
							</div>
							
						</div>
						<span>Enter ${listing.minBid } or more</span>
						<form:hidden path="auctionId" />
					</form:form>
					
				</div>
				<div class="row">
					<div class="description-header">
						<br>Description:
					</div>
				</div>
				<br>
				<div class= "dark-blue-color">${listing.description }</div>
				
				 </div>
				
			</div>
		

	</section>

	<jsp:include page="footer.jsp"></jsp:include>

	<script type="text/javascript"
		src="<c:url value="/resources/javascript/SingleItemCountDownTimer.js" />"></script>
</body>
</html>