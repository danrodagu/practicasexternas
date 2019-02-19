<%--
 * password.tag

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
 
<%@ attribute name="path" required="true" rtexprvalue="true" %>
<%@ attribute name="code" required="true" rtexprvalue="true" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="cssClass" required="true" %>

<%@ attribute name="required" required="false" %>


<%-- Definition --%>

<div>
	<label for="${id}"><spring:message code="${code}" /></label>
	<jstl:if test="${empty required}">
		<form:password id="${id}" path="${path}" cssClass="${cssClass}"/>
	</jstl:if>
	<jstl:if test="${not empty required}">
		<form:password id="${id}" path="${path}" cssClass="${cssClass}" required="${required}"/>
	</jstl:if>
	
	<br />	
	<form:errors cssClass="alert alert-danger medium" path="${path}" />	
	
</div>
