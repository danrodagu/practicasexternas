<%--
 * checkbox.tag

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

<%@ attribute name="link" required="false" %>

<%-- Definition --%>

	<form:checkbox path="${path}" />
	<form:label path="${path}">
			<jstl:if test="${link != null}">
				<a href="${link}" target="_blank"><spring:message code="${code}" /></a>
			</jstl:if>
			
			<jstl:if test="${link == null}">
				<spring:message code="${code}" />
			</jstl:if>
	</form:label><br/>
		
