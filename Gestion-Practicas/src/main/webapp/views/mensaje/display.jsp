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

<spring:message code="mensaje.emisor" />
&nbsp;&nbsp;:
<jstl:out value="${util.checkEmailAndNumber(mensaje.emisor.name)}" />
<br />

<spring:message code="mensaje.receptor" />
&nbsp;&nbsp;: ${util.checkEmailAndNumber(mensaje.receptor.name)}
<br />

<spring:message code="mensaje.fecha" />
&nbsp;&nbsp;:
<jstl:out value="${mensaje.fecha}" />
<br />

<spring:message code="mensaje.asunto" />
&nbsp;&nbsp;:
<jstl:out value="${util.checkEmailAndNumber(mensaje.asunto)}" />
<br />

<spring:message code="mensaje.cuerpo" />
&nbsp;&nbsp;:
<pre><jstl:out value="${util.checkEmailAndNumber(mensaje.cuerpo)}" /></pre>
<br /><br />


