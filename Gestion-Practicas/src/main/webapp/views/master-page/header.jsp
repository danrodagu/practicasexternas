<%--
 * header.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html lang="en">
	<!-- Bootstrap -->
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="styles/common.css" type="text/css">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
	<!-- jQuery, Popper.js y Bootstrap JS -->
	    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
	
	<br/>
	
	<div>
		<img src="images/logo.png" alt="Sample Co., Inc." />
	</div>
	
	<div>
		<ul id="jMenu">
			<!-- Do not forget the "fNiv" class for the first level links !! -->
			<security:authorize access="hasRole('ADMIN')">
				<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
					<ul>
						<li class="arrow"></li>
						<li><a href="administrator/action-1.do"><spring:message code="master.page.administrator.action.1" /></a></li>
						<li><a href="administrator/action-2.do"><spring:message code="master.page.administrator.action.2" /></a></li>					
					</ul>
				</li>
			</security:authorize>
			
			<security:authorize access="hasRole('CUSTOMER')">
				<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
					<ul>
						<li class="arrow"></li>
						<li><a href="customer/action-1.do"><spring:message code="master.page.customer.action.1" /></a></li>
						<li><a href="customer/action-2.do"><spring:message code="master.page.customer.action.2" /></a></li>					
					</ul>
				</li>
			</security:authorize>
			
			<security:authorize access="isAnonymous()">
				<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			</security:authorize>
			
			<security:authorize access="isAuthenticated()">
				<li>
					<a class="fNiv"> 
						<spring:message code="master.page.profile" /> 
				        (<security:authentication property="principal.username" />)
					</a>
					<ul>
						<li class="arrow"></li>
						<li><a href="profile/action-1.do"><spring:message code="master.page.profile.action.1" /></a></li>
						<li><a href="profile/action-2.do"><spring:message code="master.page.profile.action.2" /></a></li>
						<li><a href="profile/action-3.do"><spring:message code="master.page.profile.action.3" /></a></li>					
						<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
					</ul>
				</li>
			</security:authorize>
		</ul>
	</div>
	
	<div>
		<a href="?language=en">en</a> | <a href="?language=es">es</a>
	</div>
</html>

