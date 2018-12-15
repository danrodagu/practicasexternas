<%--
 * list.jsp

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

<display:table name="carpetas" id="row" requestURI="carpeta/list.do"
	pagesize="5" class="table table-hover">

	<display:footer>
		<gp:buttonUrl url="mensaje/create.do" code="mensaje.create"/>
	</display:footer>

	<spring:message code="carpeta.nombre" var="nameHeader" />
	<display:column property="nombre" title="${nameHeader}" />
	
	<display:column>
		<gp:iconUrl url="mensaje/list.do?carpetaId=${row.id}" icon="fas fa-envelope" name="carpeta.mensajes" color="Crimson"/>
	</display:column>
	
<%-- 	<display:column>
		<jstl:if test="${row.noModificable == false }">
			<gp:buttonUrl url="carpeta/edit.do?carpetaId=${row.id}" code="carpeta.editar"/>
		</jstl:if>
	</display:column> --%>

</display:table>

<%-- <a href="carpeta/create.do" class="btn btn-success"> <spring:message
		code="carpeta.create" />
</a> --%>
