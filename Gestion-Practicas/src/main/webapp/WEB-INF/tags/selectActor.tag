<%--
 * select.tag

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
<%@ attribute name="items" required="true" type="java.util.Collection" %>
<%@ attribute name="itemLabel" required="true" %>
<%@ attribute name="cssClass" required="false" %>
<%@ attribute name="itemValue" required="true" %>

<%@ attribute name="id" required="false" %>
<%@ attribute name="onchange" required="false" %>

<jstl:if test="${id == null}">
	<jstl:set var="id" value="${UUID.randomUUID().toString()}" />
</jstl:if>

<jstl:if test="${onchange == null}">
	<jstl:set var="onchange" value="javascript: return true;" />
</jstl:if>

<%-- Definition --%>

<div>
	<spring:message code="master.page.select" var="optVacio" />
	<form:label path="${path}">
		<spring:message code="${code}" />
	</form:label>	
	<form:select id="${id}" path="${path}" onchange="${onchange}" cssClass="${cssClass}" >
		<form:option value="" label="${optVacio}" />		
		<form:options items="${items}" itemValue="${itemValue}" itemLabel="${itemLabel}" />
	</form:select>
	<br />	
	<form:errors cssClass="alert alert-danger medium" path="${path}" />	
</div>


