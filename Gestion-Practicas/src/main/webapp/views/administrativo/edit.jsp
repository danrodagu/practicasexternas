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
	<form:form action="administrativo/edit.do" modelAttribute="administrativoForm">
		<form:hidden path="id" />

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
				<gp:textbox id="username" code="actor.username" path="username" cssClass="form-control" required="required" />
			</div>
			<div class="form-group col-md-4">
				<gp:textbox id="email" code="actor.email" path="email" cssClass="form-control" required="required" />
			</div>
		</div>
		
		<jstl:if test="${administrativoForm.id != 0}">
			<div class="row">
				<div class="form-group col-md-4">
					<gp:password id="password" code="actor.password" path="password" cssClass="form-control" required="required" />
				</div>
				<div class="form-group col-md-4">
					<gp:password id="password2" code="actor.password2" path="password2" cssClass="form-control" required="required"/>				
				</div>
			</div>
		</jstl:if>
		<br />
		<br />
		
		
		<button name="save" type="submit" class="btn btn-dark"><spring:message code="crear.submit" /></button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-dark" onclick="javascript: window.location.replace('welcome/index.do');"  >
			<spring:message code="actor.cancel" />
		</button>

		
		<%-- <gp:textbox code="actor.picture" path="picture" />
		<br /> --%>
		
				
		
	</form:form>

</fieldset>
