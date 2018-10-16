<%--
 * create.jsp

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

<display:table name="mensajes" id="row" requestURI="${requestURI}"
	pagesize="10" class="displaytag">
	
	<spring:message code="mensaje.fecha" var="fechaHeader" />
	<display:column property="fecha" title="${fechaHeader}" sortable="true" />

	<spring:message code="mensaje.emisor" var="emisorHeader" />
	<display:column property="emisor.userAccount.username" title="${emisorHeader}" />

	<spring:message code="mensaje.receptor" var="receptorHeader" />
	<display:column property="receptor.userAccount.username" title="${receptorHeader}" />

	<spring:message code="mensaje.asunto" var="asuntoHeader" />
	<display:column property="asunto" title="${asuntoHeader}" />

	<display:column>
		<gp:buttonUrl url="mensaje/reply.do?mensajeId=${row.id}&actorId=${row.emisor.id}" code="mensaje.reply"/>
	</display:column>
	
	<display:column>
		<gp:buttonUrl url="mensaje/forward.do?mensajeId=${row.id}" code="mensaje.forward"/>
	</display:column>
	
	<display:column>
		<gp:buttonUrl url="mensaje/display.do?mensajeId=${row.id}" code="mensaje.display"/>
	</display:column>
	
	<display:column>
		<a  onclick="return confirm('<spring:message code="mensaje.delete.confirm" />')" href="mensaje/delete.do?mensajeId=${row.id}"> <spring:message code="mensaje.delete" />
		</a>
	</display:column>
	

</display:table>
