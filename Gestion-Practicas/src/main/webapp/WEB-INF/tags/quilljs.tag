<%--
 * quilljs.tag

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
<%@ attribute name="readonly" required="false" %>
<%@ attribute name="placeholder" required="false" %>
<%@ attribute name="cssClass" required="true" %>
<%@ attribute name="id" required="true" %>

<%@ attribute name="required" required="false" %>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>

<jstl:if test="${required == null}">
	<jstl:set var="required" value="false" />
</jstl:if>

<%-- Definition --%>


<form:textarea id="${id}" path="${path}" readonly="${readonly}" cssClass="${cssClass}" required="${required}" placeholder="${placeholder}"/>
<br />
<form:errors cssClass="alert alert-danger medium" path="${path}" />


