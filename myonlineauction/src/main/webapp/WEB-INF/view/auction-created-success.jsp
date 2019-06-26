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
		<p>Your listing has been created</p>
	</section>
	<a href="${pageContext.request.contextPath}/">Home</a>
	<a href="${pageContext.request.contextPath}/auction/${listing}">Created listing</a>
	<jsp:include page="footer.jsp"></jsp:include>
</body>


</html>



