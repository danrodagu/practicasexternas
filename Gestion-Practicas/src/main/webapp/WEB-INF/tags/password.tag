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

<jstl:if test="${required == null}">
	<jstl:set var="required" value="false" />
</jstl:if>

<%-- Definition --%>

<div>
	<label for="${id}"><spring:message code="${code}" /></label>
	<form:password id="${id}" path="${path}" cssClass="${cssClass}"/>
	<form:errors path="${path}" cssClass="error" />
</div>
