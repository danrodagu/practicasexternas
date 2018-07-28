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

<%-- <spring:message code="alumno.picture" />
:&nbsp;&nbsp;
<img src="${alumno.picture}" height="150" />
<br />
<br /> --%>

<spring:message code="alumno.nombre" />:
&nbsp;&nbsp;
<jstl:out value="${alumno.nombre}" />
<br />

<spring:message code="alumno.apellidos" />:
&nbsp;&nbsp;
<jstl:out value="${alumno.apellidos}" />
<br />


