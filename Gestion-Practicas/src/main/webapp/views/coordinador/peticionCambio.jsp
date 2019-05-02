<%--
 * peticionCambio.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="gp" tagdir="/WEB-INF/tags"%>

<fieldset>
	<form:form action="coordinador/peticionCambio.do" modelAttribute="form">
		
		<spring:message code="coordinador.peticionCambio.msg" />
		<br />
		<br />
		
		<div class="row">
			<div class="form-group col-md-4">
				<gp:textbox id="email" code="actor.email" path="email" cssClass="form-control" required="required" />
			</div>
		</div>		
		
		<br />
		<br />		
		
		<spring:message code="coordinador.peticionCambio.confirm" var="confirmHeader" />
		<button name="save" type="submit" onclick="return confirm('${confirmHeader}')" class="btn btn-dark"><spring:message code="crear.submit" /></button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-dark" onclick="javascript: window.location.replace('welcome/index.do');"  >
			<spring:message code="actor.cancel" />
		</button>				
		
	</form:form>

</fieldset>
