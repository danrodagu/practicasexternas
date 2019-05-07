<%--
 * footer.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="gp" tagdir="/WEB-INF/tags"%>

<jsp:useBean id="date" class="java.util.Date" />

<!DOCTYPE html>
<html lang="es">
	<!-- <link rel="stylesheet" href="styles/displaytag.css" type="text/css">
	<script type="text/javascript" src="scripts/jmenu.js"></script>
	<link rel="stylesheet" href="styles/jmenu.css" media="screen" type="text/css" /> -->
	
	<!-- Bootstrap -->
<!-- 	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
	<link rel="stylesheet" href="styles/common.css" type="text/css"> -->
	<!-- <script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
	
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="https://cdn.quilljs.com/1.3.6/quill.js"></script> -->
	
	
	<!-- <div class="footer text-center"> -->
	<div  align="center" style="width: 100%;bottom: 0;clear: both;padding-top: 20px;">
		<hr />
		<p class="small">Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Escuela Técnica Superior de Ingeniería Informática</b>
	</div>
</html>