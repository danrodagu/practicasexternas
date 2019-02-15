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
<form:form action="alumno/list.do?listAll=0" modelAttribute="busquedaAlumnosForm">

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
	</div>
	
	<div class="row">
		<div class="form-group col-md-4">							
			<form:label path="tienePracticaAbierta">
				<spring:message code="alumno.search.practicaAbierta" />
			</form:label>	
			<form:select id="tienePracticaAbierta" path="tienePracticaAbierta" cssClass="form-control" >
				<spring:message code="master.page.select" var="optVacio" />
				<form:option value="" label="${optVacio}" />
				<form:option id="si" value="true" label="S�" />
				<form:option id="no" value="false" label="No" />						
			</form:select>
			<br />	
			<form:errors cssClass="alert alert-danger medium" path="tienePracticaAbierta" />			
		</div>
	</div>
	<br />
	<br />
	
	
	<button name="search" type="submit" class="btn btn-dark"><spring:message code="alumno.search.btn" /></button>				
	
</form:form>
	
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

<script type="text/javascript">
	$(document).ready(function() {
		if(localStorage.getItem("language") == "en"){
			$('select').find('option').contents().each(function(){				
				if(this.textContent == 'S�'){
					this.textContent = 'Yes';
				}
			});
		}		
	});
</script>
