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
	<section class="login-section">
		<div class="row">
			<h1>Sign in</h1>
		</div>
		<div class="row">
		<form class="registration-form blue-shadow"
			action="${pageContext.request.contextPath}/authenticateTheUser"
			method="POST">
			<c:if test="${param.error != null }">
				<div class="row error">
					Sorry! You entered invalid username/password
				</div>
				
			</c:if>
			
			
			<div class="row home-search-block">
                        <div class="col span-1-of-3">
                            <label class="dark-blue-color">Username</label>
                        </div>
                        <div class="col span-2-of-3">
                            <input type="text" name="username">
                        
                        </div>
                    </div>
                    <div class="row  home-search-block">
                        <div class="col span-1-of-3">
                            <label class="dark-blue-color">Password</label>
                        </div>
                        <div class="col span-2-of-3">
                            <input type="password" name="password">
                        
                        </div>
                    </div>
                    <div class="row">
                    
                    	<div class="center-button">
							<input class="place-bid" type="submit" value="Login">
					</div>
                        
                    </div>
		</form>
		</div>
	</section>
	
	<jsp:include page="footer.jsp"></jsp:include>
</body>


</html>



