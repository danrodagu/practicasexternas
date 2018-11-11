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

<spring:message code="mensaje.emisor" />:
&nbsp;&nbsp;
<jstl:out value="${mensaje.emisor.nombre}" />
<br />

<spring:message code="mensaje.receptor" />:
&nbsp;&nbsp;
<jstl:out value="${mensaje.receptor.nombre}" />
<br />

<spring:message code="mensaje.fecha" />:
&nbsp;&nbsp;
<jstl:out value="${mensaje.fecha}" />
<br />

<spring:message code="mensaje.asunto" />:
&nbsp;&nbsp;
<jstl:out value="${mensaje.asunto}" />
<br />

<spring:message code="mensaje.cuerpo" />:
&nbsp;&nbsp;
<div id="cuerpo"><jstl:out value="${mensaje.cuerpo}" /></div>
<br /><br />


<script type='text/javascript'>
	$(document).ready(function() {
		var res = document.getElementById("cuerpo").textContent;
	    $('#cuerpo').empty();
	    $('#cuerpo').append(res);
	});
</script>

