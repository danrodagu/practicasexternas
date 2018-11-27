<%--
 * layout.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="shortcut icon" href="favicon.ico"/> 

<link rel="stylesheet" href="styles/displaytag.css" type="text/css">
<script type="text/javascript" src="scripts/jmenu.js"></script>
<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/jmenu.css" media="screen" type="text/css" />

<!-- Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">


<script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>

<!-- FontAwesome (iconos) -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">

<!-- Quilljs -->
<link href="https://cdn.quilljs.com/1.0.0/quill.snow.css" rel="stylesheet">
<script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>

<title><tiles:insertAttribute name="title" ignore="true" /></title>

<!-- <script type="text/javascript">
	$(document).ready(function() {
		$("#jMenu").jMenu();
	});

	function askSubmission(msg, form) {
		if (confirm(msg))
			form.submit();
	}
</script> -->



</head>

<body>

	<div>
		<tiles:insertAttribute name="header" />
	</div>
	<div class="container">
		<div>
			<h1>
				<tiles:insertAttribute name="title" />
			</h1>
			<tiles:insertAttribute name="body" />	
			<jstl:if test="${message != null}">
				<br />
				<jstl:if test="${fn:contains(message, 'error')}">
					<div class="alert alert-danger medium" role="alert">
						<spring:message code="${message}" />
					</div>
				</jstl:if>
				<jstl:if test="${fn:contains(message, 'success')}">
					<div class="alert alert-success medium" role="alert">
						<spring:message code="${message}" />
					</div>
				</jstl:if>
				
			</jstl:if>	
		</div>
	</div>

</body>
<div>
	<tiles:insertAttribute name="footer" />
</div>


<script type="text/javascript">		
	var path = '<%= session.getAttribute("active") %>';
	$(document).ready(function() {		  
		  $('nav .nav-link .active').removeClass('active');
		  $('#'+path).addClass('active');
		  
		  
		});
</script>

<script type="text/javascript">
	if(localStorage.getItem("language") == "en"){
		$( "form" ).find(".form-control").attr("oninvalid", "this.setCustomValidity('Fill out this field')");
		$( "form" ).find(".form-control").attr("oninput", "this.setCustomValidity('')");
	}
	if(localStorage.getItem("language") == "es"){
		$( "form" ).find(".form-control").attr("oninvalid", "this.setCustomValidity('Completa este campo')");
		$( "form" ).find(".form-control").attr("oninput", "this.setCustomValidity('')");
	}
</script>



</html>