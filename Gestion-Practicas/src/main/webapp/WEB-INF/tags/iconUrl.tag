<%--
 * iconUrl.tag

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
<%@ attribute name="url" required="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="icon" required="true" %>
<%@ attribute name="color" required="false" %>
<%@ attribute name="target" required="false" %>


<%-- Definition --%>
<spring:message code="${name}" var="nameHeader" />
<a href="${url}" target="${target}" title="${nameHeader}">
<span style="font-size: 20px; color: ${color};">
	<i class="${icon}"></i>
</span>
</a>