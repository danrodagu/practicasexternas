<%--
 * create.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="gp" tagdir="/WEB-INF/tags"%>

<fieldset>
	<form:form action="alumno/create.do" modelAttribute="registroAlumnoForm">

	<div class="dropdown-divider"></div>
		<h4><spring:message code="alumno.registro.datosAlumno" /></h4>
		<br />

		<div class="row">
			<div class="form-group col-md-4">
				<gp:textbox id="nombre" code="actor.nombre" path="nombre" cssClass="form-control" required="required" />
			</div>
			<div class="form-group col-md-4">
				<gp:textbox id="apellidos" code="actor.apellidos" path="apellidos" cssClass="form-control" required="required"/>				
			</div>
		</div>
		
		<div class="row">
			<div class="form-group col-md-4">
				<gp:textbox id="username" code="actor.username" path="username" cssClass="form-control" required="required" />
			</div>
		</div>
		
		<div class="row">
			<div class="form-group col-md-4">
				<gp:password id="password" code="actor.password" path="password" cssClass="form-control" required="required" />
			</div>
			<div class="form-group col-md-4">
				<gp:password id="password2" code="actor.password2" path="password2" cssClass="form-control" required="required"/>				
			</div>
		</div>
		
		
		<div class="dropdown-divider"></div>
		
		<h4><spring:message code="alumno.registro.datosOferta" /></h4>
		<br />
		
		
		<div class="row">
			<div class="form-group col-md-4">
				<gp:textbox id="titulo" code="oferta.titulo" path="titulo" cssClass="form-control" required="required" />
			</div>
			<div class="form-group col-md-4">
				<gp:textarea id="descripcion" code="oferta.descripcion" path="descripcion" cssClass="form-control" required="required"/>				
			</div>
			<div class="form-group col-md-4">
				<gp:textbox id="empresa" code="oferta.empresa" path="empresa" cssClass="form-control" required="required"/>				
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-4">
				<gp:textbox id="duracion" code="oferta.duracion" path="duracion" cssClass="form-control" required="required" />
			</div>
			<div class="form-group col-md-4">
				<gp:textbox id="dotacion" code="oferta.dotacion" path="dotacion" cssClass="form-control" required="required"/>				
			</div>
		</div>

		<div class="row">
			<div class="form-group col-md-4">
				<%-- <gp:select id="esCurricular" code="oferta.esCurricular" path="esCurricular" items="valores" itemLabel="opciones" cssClass="form-control" /> --%>
				<div>
					<form:label path="esCurricular">
						<spring:message code="oferta.esCurricular" />
					</form:label>	
					<spring:message code="oferta.esCurricular.curricular" var="curricular"/>
					<spring:message code="oferta.esCurricular.extracurricular" var="extracurricular"/>
					<form:select id="esCurricular" path="esCurricular" cssClass="form-control" required="required">
						<form:option value="true" label="${curricular}" />
						<form:option value="false" label="${extracurricular}" />
					</form:select>
					<br />	
					<form:errors cssClass="alert alert-danger medium" path="${path}" />	
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="form-group col-md-4">
				<gp:textbox id="pais" code="oferta.pais" path="pais" cssClass="form-control" required="required" />
			</div>
			<div class="form-group col-md-4">
				<gp:textbox id="provincia" code="oferta.provincia" path="provincia" cssClass="form-control" required="required" />			
			</div>
			<div class="form-group col-md-4">
				<gp:textbox id="localidad" code="oferta.localidad" path="localidad" cssClass="form-control" required="required"/>				
			</div>
		</div>
		
		<div class="dropdown-divider"></div>
		
		<h4><spring:message code="alumno.registro.datosTutor" /></h4>
		<br />
		
		<div class="row">
			<div class="form-group col-md-4">
				<gp:select id="tutor" code="oferta.tutor" path="idTutor" items="${tutores}" itemLabel="nombreCompleto" cssClass="form-control" />
			</div>
		</div>
		
		<button name="save" type="submit" class="btn btn-dark"><spring:message code="crear.submit" /></button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-dark" onclick="javascript: window.location.replace('welcome/index.do');"  >
			<spring:message code="actor.cancel" />
		</button>

		
		<%-- <gp:textbox code="actor.picture" path="picture" />
		<br /> --%>
		
				
		
	</form:form>

</fieldset>
