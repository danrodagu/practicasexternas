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

<form:form action="alumno/list.do?listAll=${listAllAlum}" modelAttribute="busquedaAlumnosForm">

	<div class="row">
		<div class="form-group col-md-4">
			<gp:textbox id="nif" code="actor.nif" path="nif" cssClass="form-control"/>
		</div>
		<div class="form-group col-md-4">
			<gp:textbox id="nombre" code="actor.nombre" path="nombre" cssClass="form-control"/>
		</div>
		<div class="form-group col-md-4">
			<gp:textbox id="apellidos" code="actor.apellidos" path="apellidos" cssClass="form-control"/>				
		</div>
	</div>
	
	<div class="row">
		<div class="form-group col-md-4">
			<gp:textbox id="titulacion" code="actor.titulacion" path="titulacion" cssClass="form-control" />
		</div>
		<div class="form-group col-md-4">							
			<form:label path="tienePracticaAbierta">
				<spring:message code="alumno.search.practicaAbierta" />
			</form:label>	
			<form:select id="tienePracticaAbierta" path="tienePracticaAbierta" cssClass="form-control" >
				<spring:message code="master.page.select" var="optVacio" />
				<form:option value="" label="${optVacio}" />
				<form:option id="si" value="true" label="Sí" />
				<form:option id="no" value="false" label="No" />						
			</form:select>
			<br />	
			<form:errors cssClass="alert alert-danger medium" path="tienePracticaAbierta" />			
		</div>
		<div class="form-group col-md-4">							
			<form:label path="activo">
				<spring:message code="alumno.search.activo" />
			</form:label>	
			<form:select id="activo" path="activo" cssClass="form-control" >
				<spring:message code="alumno.search.activo.indiferente" var="optVacio" />
				<form:option value="" label="${optVacio}" />
				<form:option id="si" value="true" label="Sí" />
				<form:option id="no" value="false" label="No" />						
			</form:select>
			<br />	
			<form:errors cssClass="alert alert-danger medium" path="activo" />			
		</div>		
		<div class="form-group col-md-4" style="position: relative; margin-top: 0.6%">
			<br />
			<button name="search" type="submit" class="btn btn-dark"><spring:message code="alumno.search.btn" /></button>				
		</div>
	</div>
	<br />
	
	
</form:form>


	
	<br />

<div class="table-responsive">
	<display:table name="alumnos" id="row" requestURI="alumno/list.do"
		pagesize="10" class="table table-hover">	
	
		<spring:message code="alumno" var="alumnoHeader" />
		<display:column property="nombreCompleto" title="${alumnoHeader}" />
		
		<spring:message code="actor.nif" var="nifHeader" />
		<display:column property="nif" title="${nifHeader}" />
		
		<spring:message code="actor.titulacion" var="titulacionHeader" />
		<display:column property="titulacion" title="${titulacionHeader}" />
				
		<display:column>
			<gp:iconUrl url="alumno/practicas.do?listAllAlum=${listAllAlum}&alumnoId=${row.id}" icon="fas fa-graduation-cap" name="alumno.practicas" color="Crimson"/>
		</display:column>
		
		<security:authorize access="hasRole('COORDINADOR') || hasRole('ADMINISTRATIVO')">
			<display:column>
				<gp:iconUrl url="actor/edit.do?actorId=${row.id}" icon="fas fa-pencil-alt" name="oferta.edit" color="Crimson"/>
			</display:column>
			<display:column>
				<jstl:if test="${row.userAccount.enabled == true}">
					<a href="actor/desactivar.do?actorId=${row.id}" class="btn btn-danger btn-sm btn-block" role="button"><spring:message code="actor.desactivar" /></a>
				</jstl:if>
				<jstl:if test="${row.userAccount.enabled == false}">
					<a href="actor/activar.do?actorId=${row.id}" class="btn btn-primary btn-sm btn-block" role="button"><spring:message code="actor.activar" /></a>
				</jstl:if>
			</display:column>
		</security:authorize>		
	</display:table>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		if(localStorage.getItem("language") == "en"){
			$('select').find('option').contents().each(function(){				
				if(this.textContent == 'Sí'){
					this.textContent = 'Yes';
				}
			});
		}		
	});
</script>
