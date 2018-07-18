<%--
 * header.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<%
        session.getAttribute("active");
%>



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
	
	<script type='text/javascript'>
	  function english() {	    
	    window.location.href  = '?language=en';
  	  }
	</script>
	
	<script type='text/javascript'>
	  function spanish() {	    
	    window.location.href  = '?language=es';
  	  }
	</script>
	
	<div class="row" style="margin-bottom:2%">
    	<div class="span4">
			<div class="clearfix content-heading">
				<h1 style="float:left;margin-left:3%;margin-top:1%;padding-right:3%">Prácticas<br />Externas</h1>
				
				<img src="images/logo-etsii.png" alt="ETSII" class="img-fluid" style="width:60%" />				
				
				<div style="float:right;padding-right:3%;">
					<div style="padding-bottom:5px;">
						<img style="cursor:pointer;width:30px;height:auto;" src="images/spainflag.png" onclick="spanish()" /> | <img style="cursor:pointer;width:30px;height:auto;" src="images/ingflag.png" onclick="english()" />
					</div>
					<br />
					<security:authorize access="isAnonymous()">
						<spring:message code="master.page.login" var="login"/>
						<input type="button" class="btn btn-dark" value="${login}" onclick="location.href = 'security/login.do';">
					</security:authorize>
				</div>	
				
					
			</div>
		</div>
	</div>
	
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
	    <span class="navbar-toggler-icon"></span>
	  </button>
	  <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
	    <div class="navbar-nav">
	      <a class="nav-item nav-link" href="welcome/index.do"><spring:message code="master.page.inicio"/><span class="sr-only">(current)</span></a>
	      <a class="nav-item nav-link" href="welcome/noticias.do"><spring:message code="master.page.noticias"/></a>
	      <a class="nav-item nav-link" href="#"><spring:message code="master.page.documentacion"/></a>
	    </div>
	  </div>
	</nav>	
	
	<%-- <div style="margin-left:1%;margin-right:2%;">
	
		<nav>
		  <div class="nav nav-tabs" id="nav-tab" role="tablist">
		    <a class="nav-item nav-link active" id="nav-inicio-tab" data-toggle="tab" href="#nav-inicio" role="tab" aria-controls="nav-inicio" aria-selected="true"><spring:message code="master.page.inicio"/></a>
		    <a class="nav-item nav-link" id="nav-noticias-tab" data-toggle="tab" href="#nav-noticias" role="tab" aria-controls="nav-noticias" aria-selected="false"><spring:message code="master.page.noticias"/></a>
		    <a class="nav-item nav-link" id="nav-documentacion-tab" data-toggle="tab" href="#nav-documentacion" role="tab" aria-controls="nav-documentacion" aria-selected="false"><spring:message code="master.page.documentacion"/></a>
		  </div>
		</nav>
		<div class="tab-content" id="nav-tabContent">
		  <div class="tab-pane fade show active" id="nav-inicio" role="tabpanel" aria-labelledby="nav-inicio-tab"><a href="views/security/login.do"></a></div>
		  <div class="tab-pane fade" id="nav-noticias" role="tabpanel" aria-labelledby="nav-noticias-tab">cuerpo noticias</div>
		  <div class="tab-pane fade" id="nav-documentacion" role="tabpanel" aria-labelledby="nav-documentacion-tab">cuerpo documentacion</div>
		</div>
	</div> --%>
		
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
		
		<script type="text/javascript">
			var path = '<%= session.getAttribute("active") %>';
			$(document).ready(function() {
				  $('nav .nav-link .active').removeClass('active');
				  $('a[href="' + path + '"]').addClass('active'); 
				});
		</script>	
</html>

