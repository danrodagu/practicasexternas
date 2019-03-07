<%--
 * header.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>

<%
        session.getAttribute("active");
%>



<!DOCTYPE html>
<html lang="es">
	
	
	<br/>
	
	<script type='text/javascript'>
		  function english() {	    
		    window.location.href  = '?language=en';
		    localStorage.setItem("language", "en");
	  	  }
		</script>
	
		<script type='text/javascript'>
		  function spanish() {	    
		    window.location.href  = '?language=es';
		    localStorage.setItem("language", "es");
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
					
					<security:authorize access="isAuthenticated()">
						<div class="btn-group dropleft" >
							<button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<%= session.getAttribute("uvus") %>
							</button>
							<div class="dropdown-menu" aria-labelledby="dropdownMenu">					
							   <a class="dropdown-item" href="actor/edit.do"><spring:message code="master.page.perfil"/></a>							   
							    <div class="dropdown-divider"></div>
							    <a class="dropdown-item" href="j_spring_security_logout"><spring:message code="master.page.logout" /></a>
							</div>
						</div>
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
	      <a id="inicio" class="nav-item nav-link" href="welcome/index.do"><spring:message code="master.page.inicio"/><span class="sr-only">(current)</span></a>
	      <a id="noticias" class="nav-item nav-link" href="welcome/noticias.do"><spring:message code="master.page.noticias"/></a>
	      <a class="nav-item nav-link" href="#"><spring:message code="master.page.documentacion"/></a>
	      <security:authorize access="hasRole('COORDINADOR') || hasRole('ADMINISTRATIVO')">
		      <div class="dropdown">
			      <a id="alta" class="nav-item nav-link dropdown-toggle" style="cursor:pointer;" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			      	<spring:message code="master.page.alta"/>
			      </a>
			      <div class="dropdown-menu" aria-labelledby="alta">
			          <a class="dropdown-item" href="alumno/create.do"><spring:message code="master.page.alumno"/></a>
			          <a class="dropdown-item" href="tutor/create.do"><spring:message code="master.page.tutor"/></a>
			          <security:authorize access="hasRole('COORDINADOR')">
			          	<a class="dropdown-item" href="administrativo/create.do"><spring:message code="master.page.administrativo"/></a>
		          	  </security:authorize>
		          	  <a class="dropdown-item" href="oferta/create.do"><spring:message code="master.page.oferta"/></a>
		          </div>
		      </div>
		      <a id="todosAlum" class="nav-item nav-link" href="alumno/list.do?listAll=1"><spring:message code="master.page.alumnos"/></a>
	      </security:authorize>
	      <security:authorize access="hasRole('COORDINADOR') || hasRole('TUTOR')">
		      <a id="alumnos" class="nav-item nav-link" href="alumno/list.do"><spring:message code="master.page.mis.alumnos"/></a>
	      </security:authorize>
	      <security:authorize access="hasRole('ALUMNO')">
	      	  <a id="practicas" class="nav-item nav-link" href="alumno/practicas.do"><spring:message code="master.page.mis.practicas"/></a>
		      <%-- <a id="documentos" class="nav-item nav-link" href="documento/list.do"><spring:message code="master.page.mis.documentos"/></a> --%>
	      </security:authorize>
	      <security:authorize access="isAuthenticated()">
	      	<a id="mensajes" class="nav-item nav-link" href="carpeta/list.do">
	      		<spring:message code="master.page.mensajes"/>
	      		<b id="contadorMsg" style="color:red;" ></b>
	      	</a>
	      </security:authorize>
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
	
	<script type="text/javascript">	
	$(document).ready(function() {
		var msgBar = undefined;
		msgBar = $("#mensajes").attr("href");
		
		if(msgBar != undefined){
			$.ajax({
				type : "POST",
				url : 'mensaje/contadorMsg.do',
				contentType: 'application/json; charset=utf-8',
			    async: false,
			    cache: false,
				success: function(callback){
					if(callback != null && callback != "0"){
						$("#contadorMsg").html("(+" + callback + ")");					
					}
					if(window.location.href.indexOf("mensaje/contadorMsg.do") > -1){
						window.location.href = "welcome/index.do";
					}
				},
				error: function (xhr) {
			        alert('Error ' + xhr.status);
			    }
			});	
		}
			
	});
	
</script>
		
</html>

