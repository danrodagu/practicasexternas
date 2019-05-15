<%--
 * invalidaEvaluacion.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="gp" tagdir="/WEB-INF/tags"%>

<fieldset>
	<form:form action="oferta/invalidaEvaluacion.do" modelAttribute="invalidaEvaluacionForm">
		<form:hidden path="idOferta" />
		
		<spring:message code="oferta.invalidaEvaluacion.msg" />
		<br />
		<br />
		
		<div class="row">
			<div class="form-group col-md-4">
				<gp:textarea id="justificacion" cols="20" rows="10" code="oferta.justificacion" path="justificacion" cssClass="form-control" required="required" />
			</div>
		</div>		
		
		<br />
		<br />		
		
		<spring:message code="oferta.invalidaEvaluacion.confirm" var="confirmHeader" />
		<button name="save" type="submit" onclick="return confirm('${confirmHeader}')" class="btn btn-primary" style="background-color: #3E58AA;border: 1px solid #39529e;color:white;"><spring:message code="crear.submit" /></button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-primary" style="background-color: #3E58AA;border: 1px solid #39529e;color:white;" onclick="javascript: window.location.replace('oferta/display.do?ofertaId=${invalidaEvaluacionForm.idOferta}');"  >
			<spring:message code="actor.cancel" />
		</button>				
		
	</form:form>

</fieldset>
