<%--
 * display.jsp

 --%>

<%@page language="java" contentType="cuerpo/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="gp" tagdir="/WEB-INF/tags"%>

<jsp:useBean id="util" class="utilities.UtilMethods" scope="page"/>

<spring:message code="noticia.fecha" />:
&nbsp;&nbsp;
<jstl:out value="${noticia.fecha}" />
<br />

<spring:message code="noticia.titulo" />:
&nbsp;&nbsp;
<jstl:out value="${noticia.titulo}" />
<br />
<br />
<%-- <spring:message code="noticia.cuerpo" />: --%>

<div class="card">
	<div class="card-body" id="cuerpo">
		<jstl:out value="${noticia.cuerpo}" />
	</div>
</div>
<br /><br />


<script type='text/javascript'>
	$(document).ready(function() {
		var res = document.getElementById("cuerpo").textContent;
	    $('#cuerpo').empty();
	    $('#cuerpo').append("<pre>" + res + "</pre>");
	});
</script>

