<%--
 * display.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="gp" tagdir="/WEB-INF/tags"%>

<jsp:useBean id="util" class="utilities.UtilMethods" scope="page"/>

<%-- <spring:message code="administrativo.picture" />
:&nbsp;&nbsp;
<img src="${administrativo.picture}" height="150" />
<br />
<br /> --%>

<spring:message code="administrativo.nombre" />:
&nbsp;&nbsp;
<jstl:out value="${administrativo.nombre}" />
<br />

<spring:message code="administrativo.apellidos" />:
&nbsp;&nbsp;
<jstl:out value="${administrativo.apellidos}" />
<br />


