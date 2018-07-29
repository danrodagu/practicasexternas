<%--
 * submit.tag

 --%>

<%@ tag language="java" body-content="empty"%>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="gp" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%>

<%@ attribute name="name" required="true"%>
<%@ attribute name="code" required="true"%>
<%@ attribute name="codeMessage" required="false"%>


<%-- Definition --%>

<jstl:if test="${codeMessage != null}">
	<button type="submit" name="${name}"
		onclick="return confirm('<spring:message code="${codeMessage}" />')">
		<spring:message code="${code}" />
	</button>
</jstl:if>

<jstl:if test="${codeMessage == null}">
	<button type="submit" name="${name}">
		<spring:message code="${code}" />
	</button>
</jstl:if>





