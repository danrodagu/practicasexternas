<%--
 * edit.jsp

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

<form:form action="carpeta/edit.do" modelAttribute="carpetaForm">

	<form:hidden path="id" />
	
	<gp:textbox id="nombre" code="carpeta.nombre" path="nombre" cssClass="form-control"/>
	<br/>

	<gp:submit code="carpeta.save" name="save" />&nbsp;
	
	<jstl:if test="${carpetaForm.id != 0}">
		<gp:delete name="delete" code="carpeta.delete" codeMessage="carpeta.confirm.delete"/>&nbsp;
	</jstl:if>
	
	<gp:cancel url="carpeta/list.do" code="carpeta.cancel"/>

</form:form>