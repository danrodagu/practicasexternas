<%--
 * edit.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="gp" tagdir="/WEB-INF/tags"%>


<fieldset>
	<form:form action="${action}" modelAttribute="ofertaForm">
		<form:hidden path="id"/>
		<h4><spring:message code="alumno.registro.datosOferta" /></h4>
		<br />
		
		
		<div class="row">
			<div class="form-group col-md-4">
				<gp:textbox id="titulo" code="oferta.titulo" path="titulo" cssClass="form-control" required="required" />
			</div>
			<div class="form-group col-md-4">
				<gp:textarea id="descripcion" code="oferta.descripcion" path="descripcion" cssClass="form-control" required="required"/>				
			</div>
			
		</div>
		<div class="row">
			<div class="form-group col-md-4">
				<gp:textbox id="empresa" code="oferta.empresa" path="empresa" cssClass="form-control" required="required"/>				
			</div>
			<div class="form-group col-md-4">
				<gp:textbox id="cifEmp" code="oferta.cifEmp" path="cifEmp" cssClass="form-control" required="required" />			
			</div>
			<div class="form-group col-md-4">
				<gp:textbox id="telefonoEmp" code="oferta.telefonoEmp" path="telefonoEmp" cssClass="form-control" required="required"/>				
			</div>
			<div class="form-group col-md-4">
				<gp:textbox id="emailEmp" code="oferta.emailEmp" path="emailEmp" cssClass="form-control" required="required"/>				
			</div>
			<div class="form-group col-md-4">
				<gp:textbox id="tutorEmp" code="oferta.tutorEmp" path="tutorEmp" cssClass="form-control" required="required"/>				
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-4">
				<gp:textbox id="duracion" code="oferta.duracion" path="duracion" cssClass="form-control" required="required" />
			</div>
			<div class="form-group col-md-4">
				<gp:textbox id="horas" code="oferta.horas" path="horas" cssClass="form-control" required="required" pattern="^[1-9][0-9]*$"/>
			</div>
			<div class="form-group col-md-4">
				<gp:textbox id="fechaInicio" code="oferta.fechaInicio" path="fechaInicio" cssClass="form-control" />
			</div>			
			<div class="form-group col-md-4">
				<gp:textbox id="fechaFin" code="oferta.fechaFin" path="fechaFin" cssClass="form-control" />
			</div>
		</div>

		<div class="row">
			<div class="form-group col-md-4">
				<gp:textbox id="dotacion" code="oferta.dotacion" path="dotacion" cssClass="form-control" required="required"/>				
			</div>
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
					<form:errors cssClass="alert alert-danger medium" path="esCurricular" />	
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
		<h4><spring:message code="alumno.registro.datosAlumno" /></h4>
		<br />
		
		<div class="row">
			<div class="form-group col-md-4">
				<gp:select id="alumno" code="oferta.alumno" path="idAlumno" items="${alumnos}" itemLabel="nombreCompleto" cssClass="form-control" />
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
		
		<button name="save" type="submit" class="btn btn-primary" style="background-color: #3E58AA;border: 1px solid #39529e;color:white;"><spring:message code="crear.submit" /></button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-primary" style="background-color: #3E58AA;border: 1px solid #39529e;color:white;" onclick="javascript: window.history.back();"  >
			<spring:message code="actor.cancel" />
		</button>
		
	</form:form>

</fieldset>


<script type="text/javascript">
	if(localStorage.getItem("language") == "es"){	
		jQuery(function($){
			$.datepicker.regional['es'] = {
				closeText: 'Cerrar',
				prevText: '&#x3c;Ant',
				nextText: 'Sig&#x3e;',
				currentText: 'Hoy',
				monthNames: ['Enero','Febrero','Marzo','Abril','Mayo','Junio',
				'Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre'],
				monthNamesShort: ['Ene','Feb','Mar','Abr','May','Jun',
				'Jul','Ago','Sep','Oct','Nov','Dic'],
				dayNames: ['Domingo','Lunes','Martes','Mi&eacute;rcoles','Jueves','Viernes','S&aacute;bado'],
				dayNamesShort: ['Dom','Lun','Mar','Mi&eacute;','Juv','Vie','S&aacute;b'],
				dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','S&aacute;'],
				weekHeader: 'Sm',
				dateFormat: 'dd/mm/yy',
				firstDay: 1,
				isRTL: false,
				showMonthAfterYear: false,
				yearSuffix: ''};
			$.datepicker.setDefaults($.datepicker.regional['es']);
		});
	};
	if(localStorage.getItem("language") == "en"){
		jQuery(function($){
			$.datepicker.regional['en'] = {				
				dateFormat: 'dd/mm/yy'
			};
			$.datepicker.setDefaults($.datepicker.regional['en']);
		});
	}
	
	$(document).ready(function() {
	    $("#fechaInicio").datepicker();
	    $("#fechaFin").datepicker();
	 });
</script>
