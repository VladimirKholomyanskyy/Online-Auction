<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<header class="blue-shadow">

	<div class="row">
		<ul class="main-links">
			<security:authorize access="isAuthenticated()">
				<li><a
					href="${pageContext.request.contextPath }/account/information">My
						Account</a></li>
				<li><a
					href="${pageContext.request.contextPath }/auction/showNewAuctionForm">Sell</a></li>
						
				<li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>

			</security:authorize>
			<security:authorize access="!isAuthenticated()">
				<li><a href="${pageContext.request.contextPath}/showLoginPage">Sign
						in</a></li>
				<li><a
					href="${pageContext.request.contextPath}/register/showRegistrationForm">Create
						an account</a></li>

			</security:authorize>
			<li><a href="${pageContext.request.contextPath}/help">Help
					&#38 Contact</a></li>
		</ul>
	</div>

	<div class="row home-search-block">
		<div class="col span-4-of-10 home-address">
			<a href="${pageContext.request.contextPath }/">tinyauction.com</a>
		</div>
		<div class="col span-6-of-10">


			<form:form class="search-form" modelAttribute="search"
				action="${pageContext.request.contextPath }/auction/search"
				method="GET">
				<div class="search-field-box">

					<form:input path="listingName" placeholder="Search" />
				</div>
				<div class="search-categories-box">
					<form:select path="categoryName">
						<form:options items="${categories }" itemLabel="name"
							itemValue="name" />
					</form:select>
				</div>

				<div>
					<input class="search-btn" type="submit" value="Search">
				</div>
			</form:form>


		</div>

	</div>

	<div class="row advanced-search">
		<a href="#">Advanced search</a>
	</div>
</header>


