<%--
 * recoverPassword.jsp

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

<jsp:useBean id="util" class="utilities.UtilMethods" scope="page"/>

<a><spring:message code="actor.recover.password.msg" /></a>
<br />
<br />

<form:form action="actor/recoverPassword.do" modelAttribute="recoverPasswordForm">
	<div class="row">
		<div class="form-group col-md-4">
			<gp:textbox id="username" code="actor.username" path="username" cssClass="form-control" required="required" />
		</div>
	</div>
	
	<button name="save" type="submit" class="btn btn-dark"><spring:message code="crear.submit" /></button>
	&nbsp;&nbsp;
	<button type="button" class="btn btn-dark" onclick="javascript: window.location.replace('security/login.do');"  >
		<spring:message code="actor.cancel" />
	</button>
</form:form>



