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


<br />
<div class="table-responsive">
	<display:table name="alumnos" id="row" requestURI="alumno/list.do"
		pagesize="10" class="table table-hover">	
	
		<spring:message code="alumno" var="alumnoHeader" />
		<display:column property="nombreCompleto" title="${alumnoHeader}" />
		
		<display:column>
			<gp:iconUrl url="alumno/practicas.do?listAllAlum=${listAllAlum}&alumnoId=${row.id}" icon="fas fa-graduation-cap" name="alumno.practicas" color="Crimson"/>
		</display:column>
	</display:table>
</div>
