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
			<h1>Create an account</h1>
		</div>
		<form:form class="registration-form blue-shadow"
			action="${pageContext.request.contextPath }/register/processRegistrationForm"
			modelAttribute="userDto" method="POST">
			
			
				
					<div>
						
						<c:if test="${registrationError != null}">

							<div>${registrationError}</div>
						</c:if>
					</div>
				

			<div class="row home-search-block">
				<div class="col span-1-of-3">
					<label class="dark-blue-color">First Name</label>
				</div>
				<div class="col span-2-of-3">
					<form:errors path="firstName" cssClass="error"/>
					<form:input path="firstName" />
				</div>
			</div>
			<div class="row home-search-block">
				<div class="col span-1-of-3">
					<label class="dark-blue-color">Last Name</label>
				</div>
				<div class="col span-2-of-3">
					<form:errors path="lastName"  cssClass="error"/>
					<form:input path="lastName" />
				</div>
			</div>

			<div class="row home-search-block">
				<div class="col span-1-of-3">
					<label class="dark-blue-color">Username</label>
				</div>
				<div class="col span-2-of-3">
					<form:errors path="userName"  cssClass="error"/>
					<form:input path="userName" />
				</div>
			</div>
			<div class="row home-search-block">
				<div class="col span-1-of-3">
					<label class="dark-blue-color">Email</label>
				</div>
				<div class="col span-2-of-3">
					<form:errors path="email"  cssClass="error"/>
					<form:input path="email" />
				</div>
			</div>
			<div class="row home-search-block">
				<div class="col span-1-of-3">
					<label class="dark-blue-color">Password</label>
				</div>
				<div class="col span-2-of-3">
					<form:errors path="password"  cssClass="error"/>
					<form:password path="password" />
				</div>
			</div>
			<div class="row home-search-block">
				<div class="col span-1-of-3">
					<label class="dark-blue-color">Confirm Password</label>
				</div>
				<div class="col span-2-of-3">
					<form:errors path="matchingPassword" cssClass="error"/>
					<form:password path="matchingPassword" />
				</div>
			</div>
			<div class="row">
				
					<div class="center-button">
						<input class="place-bid" type="submit" value="Register">
				</div>
				
				
			</div>




		</form:form>

	</section>
	<jsp:include page="footer.jsp"></jsp:include>
</body>


</html>