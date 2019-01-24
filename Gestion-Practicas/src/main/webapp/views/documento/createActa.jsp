<%--
 * createActa.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="gp" tagdir="/WEB-INF/tags"%>

<br />	
<fieldset>
	<form:form action="documento/acta/create.do" modelAttribute="actaForm">
		<form:hidden path="idOferta" />

		<div class="row">
			<div class="form-group col-md-4">
				<gp:textbox id="curso" code="acta.curso" path="curso" cssClass="form-control" required="required" />
			</div>
		</div>
		
		<div class="row">
			<div class="form-group col-md-4">
				<gp:textbox id="convocatoria" code="acta.convocatoria" path="convocatoria" cssClass="form-control" required="required" />
			</div>
		</div>
		
		
		<br />		
		<button name="save" type="submit" class="btn btn-dark"><spring:message code="acta.submit" /></button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-dark" onclick="javascript: window.location.replace('oferta/display.do?ofertaId=${actaForm.idOferta}');"  >
			<spring:message code="actor.cancel" />
		</button>				
		
	</form:form>

</fieldset>