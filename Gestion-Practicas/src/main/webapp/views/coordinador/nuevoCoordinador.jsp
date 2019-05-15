<%--
 * nuevoCoordinador.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="gp" tagdir="/WEB-INF/tags"%>

<fieldset>
	<jstl:if test="${faseForm == 1}">
		<jstl:set var="nuevoCoordiFormName" value="nuevoCoordiForm1"></jstl:set>
		<jstl:set var="action" value="coordinador/nuevoCoordinador1.do"></jstl:set>
	</jstl:if>
	<jstl:if test="${faseForm == 2}">
		<jstl:set var="nuevoCoordiFormName" value="nuevoCoordiForm2"></jstl:set>
		<jstl:set var="action" value="coordinador/nuevoCoordinador2.do"></jstl:set>
	</jstl:if>
	
	<form:form action="${action}" modelAttribute="${nuevoCoordiFormName}">
		
		<jstl:if test="${faseForm == 1}">
			<div class="row">
				<div class="form-group col-md-4">
					<gp:textbox id="username" code="actor.username" path="uvus" cssClass="form-control" required="required" />
				</div>
			</div>	
		</jstl:if>
		
		<jstl:if test="${faseForm == 2}">
			<div class="row">
				<div class="form-group col-md-4">
					<gp:textbox id="nif" code="actor.nif" path="nif" cssClass="form-control" required="required" />
				</div>
				<div class="form-group col-md-4">
					<gp:textbox id="nombre" code="actor.nombre" path="nombre" cssClass="form-control" required="required" />
				</div>
				<div class="form-group col-md-4">
					<gp:textbox id="apellidos" code="actor.apellidos" path="apellidos" cssClass="form-control" required="required"/>				
				</div>
			</div>
		
			<div class="row">
				<div class="form-group col-md-4">
					<gp:textbox id="departamento" code="actor.departamento" path="departamento" cssClass="form-control" required="required" />
				</div>
				<div class="form-group col-md-4">
					<gp:textbox id="username" code="actor.username" path="username" cssClass="form-control" required="required" readonly="true"/>
				</div>
				<div class="form-group col-md-4">
					<gp:textbox id="email" code="actor.email" path="email" cssClass="form-control" required="required" />
				</div>
			</div>
		</jstl:if>
			
		
		<br />
		<br />		
		
		<button name="save" type="submit" class="btn btn-primary" style="background-color: #3E58AA;border: 1px solid #39529e;color:white;"><spring:message code="crear.submit" /></button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-primary" style="background-color: #3E58AA;border: 1px solid #39529e;color:white;" onclick="javascript: window.location.replace('welcome/index.do');"  >
			<spring:message code="actor.cancel" />
		</button>				
		
	</form:form>

</fieldset>
