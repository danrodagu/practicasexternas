<%--
 * create.jsp

 --%>


<%@page language="java" contentType="cuerpo/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="gp" tagdir="/WEB-INF/tags"%>

<form:form action="mensaje/edit.do" modelAttribute="mensajeForm">

	<fieldset>
		<gp:selectActor id="receptor" items="${actores}" itemLabel="userAccount.username" itemValue="userAccount.id" code="mensaje.receptor" path="idReceptor" cssClass="form-control" />
		<br />

		<gp:textbox id="asunto" code="mensaje.asunto" path="asunto" cssClass="form-control" />
		<br />

		<gp:textarea id="cuerpo" code="mensaje.cuerpo" path="cuerpo" cssClass="form-control" />
		<br />
		

		<%-- <gp:submit name="save" code="mensaje.save" />
		&nbsp;
		<gp:cancel url="welcome/index.do" code="mensaje.cancel" /> --%>
		
		<button name="save" type="submit" class="btn btn-dark"><spring:message code="mensaje.submit" /></button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-dark" onclick="javascript: window.location.replace('welcome/index.do');">
			<spring:message code="actor.cancel" />
		</button>

	</fieldset>
</form:form>