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
	<section>
		<div class="row">
			<h1>Create a listing</h1>
		</div>

		<form:form class="registration-form"
			action="${pageContext.request.contextPath}/auction/processNewAuctionForm"
			enctype="multipart/form-data" modelAttribute="newAuctionDto">


			<div class="row home-search-block">
				<div class="col span-1-of-3">
					<label>Name</label>
				</div>
				<div class="col span-2-of-3">
					<form:errors path="listingName" cssClass = "error"/>
					<form:input path="listingName" />
				</div>
			</div>
			<div class="row home-search-block">
				<div class="col span-1-of-3">
					<label>Category</label>
				</div>
				<div class="col span-2-of-3">
					<form:select path="categoryName">
						<form:option value="-1" label="--Please select" />
						<form:options items="${categoriesList }" itemValue="name"
							itemLabel="name" />
					</form:select>

				</div>

			</div>
			<div class="row home-search-block">
				<div class="col span-1-of-3">
					<label>Price</label>
				</div>
				<div class="col span-2-of-3">
					<div class="row">
						<div class="inline-block new-listing-price">
						<form:errors path = "startPrice" cssClass = "error"/>
						<form:input  path="startPrice" />
					</div>
					<div class="inline-block">
						<form:select path="currency" items="${currencies }" itemValue="name" itemLabel="name">
							
						</form:select>
					</div>
					
					</div>
					


				</div>
			</div>

			<div class="row home-search-block">
				<div class="col span-1-of-3">
					<label>Duration</label>
				</div>
				<div class="col span-2-of-3">
					<div class="inline-block">
						<form:select path="duration" items="${durations }">
							
						</form:select>
					</div>

				</div>
			</div>

			<div class="row home-search-block">
				<div class="col span-1-of-3">
					<label>Image</label>
				</div>
				<div class="col span-2-of-3">
					<form:input type="file" path="file" />
				</div>
			</div>

			<div class="row">
				<div class="col span-1-of-3">
					<label>Description</label>
				</div>
				<div class="col span-2-of-3">
					<form:textarea path="description" />
				</div>
			</div>
			<div class="row">
				<div class="col span-1-of-3">
					<label>&nbsp;</label>
				</div>
				<div class="col span-2-of-3">
					<input class="place-bid" type="submit" value="Create">
				</div>
			</div>




		</form:form>
	</section>
	<jsp:include page="footer.jsp"></jsp:include>
</body>


</html>