<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>Listings</title>
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
	<section class="items-section blue-shadow">


		<input type="hidden" id="listLength" value="${fn:length(listings)}">

		<ul class="list-of-items">
			<c:forEach items="${listings }" var="listing" varStatus="loop">

				<li class="row blue-shadow">
					<div class="col span-1-of-5">
						<div class="image-section">
							<a
								href="${pageContext.request.contextPath}/auction/${categoryName}/${listing.id}"><img
								src="${listing.imagePath }" alt="Image"></a>
						</div>

					</div>
					<div class="col span-4-of-5">
						<div class="row">
							<a href="${pageContext.request.contextPath}/auction/${categoryName}/${listing.id}" class="item-in-list-header">${listing.name}</a>
						</div>
						<div class="row">
							<div class="col span-1-of-2">
								<div class="price">${listing.currentBid}</div>
							</div>
							<div class="col span-1-of-2">
								<div class="bids">${listing.numberOfBids}&nbsp bids</div>


								<div class="timeleft" id="timeLeft${loop.index }">${loop.index }</div>

								<input type="hidden" id="endTime${loop.index }"
									value="${listing.endTime}">
							</div>
						</div>

					</div>

				</li>
			</c:forEach>


		</ul>
		<div class="row">

			<ul class="page-nav">

				<c:if test="${prevPage != -1}">
					<c:url var="prevLink" value="/auction/${categoryName}">
						<c:param name="pageNumber" value="${prevPage}" />
						
					</c:url>
					<li><a href="${prevLink }">&#60</a></li>

				</c:if>

				<c:if test="${prevPage == -1}">

					<li>&#60</li>

				</c:if>



				<c:forEach items="${pages }" var="page">

					<c:url var="auctionsLink"
						value="/auction/${categoryName}">
						<c:param name="pageNumber" value="${page}" />
						
					</c:url>

					<c:if test="${page==currentPage }">
						<li><a class="current" href="${auctionsLink }">${page}</a></li>
					</c:if>

					<c:if test="${page!=currentPage }">
						<li><a href="${auctionsLink }">${page}</a></li>
					</c:if>

				</c:forEach>

				<c:if test="${nextPage != -1}">
					<c:url var="nextLink" value="/auction/${categoryName}">
						<c:param name="pageNumber" value="${nextPage}" />
						
					</c:url>
					<li><a href="${nextLink }">&#62</a></li>

				</c:if>

				<c:if test="${nextPage == -1}">

					<li>&#62</li>

				</c:if>

			</ul>

		</div>
	</section>

	<jsp:include page="footer.jsp"></jsp:include>

	<script type="text/javascript"
		src="<c:url value="/resources/javascript/CountDownTimer.js" />"></script>
</body>
</html>