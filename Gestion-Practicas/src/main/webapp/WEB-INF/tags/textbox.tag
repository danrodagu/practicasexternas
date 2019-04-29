<%--
 * textbox.tag

 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="gp" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 
 
<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="cssClass" required="true" %>

<%@ attribute name="title" required="false" %>
<%@ attribute name="required" required="false" %>
<%@ attribute name="readonly" required="false" %>
<%@ attribute name="placeholder" required="false" %>
<%@ attribute name="pattern" required="false" %>
<%@ attribute name="oninvalidMsg" required="false" %>

<%-- <jstl:if test="${empty oninvalidMsg}">
	<spring:message code="master.page.oninvalid" var="oninvalid"/>
</jstl:if>
<jstl:if test="${not empty oninvalidMsg}">
	<spring:message code="${oninvalidMsg}" var="oninvalid"/>
</jstl:if> --%>

<%-- Definition --%>
<div>
	<label for="${id}"><spring:message code="${code}" /></label>
	<jstl:if test="${empty required && empty readonly}">
		<form:input id="${id}" path="${path}" cssClass="${cssClass}" placeholder="${placeholder}" pattern="${pattern}" title="${title}" />
	</jstl:if>
	<jstl:if test="${empty required && not empty readonly}">
		<form:input id="${id}" path="${path}" readonly="${readonly}" cssClass="${cssClass}" placeholder="${placeholder}" pattern="${pattern}" title="${title}" />
	</jstl:if>
	<jstl:if test="${not empty required && empty readonly}">
		<form:input id="${id}" path="${path}" cssClass="${cssClass}" required="${required}" placeholder="${placeholder}" pattern="${pattern}" title="${title}" />
	</jstl:if>
	<jstl:if test="${not empty required && not empty readonly}">
		<form:input id="${id}" path="${path}" readonly="${readonly}" cssClass="${cssClass}" required="${required}" placeholder="${placeholder}" pattern="${pattern}" title="${title}" />
	</jstl:if>
		
	<br />	
	<form:errors cssClass="alert alert-danger medium" path="${path}" />	
</div>
