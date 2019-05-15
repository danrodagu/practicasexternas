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
			<%-- <div class="form-group col-md-4">
				<gp:textbox id="convocatoria" code="acta.convocatoria" path="convocatoria" cssClass="form-control" required="required" />
			</div> --%>
			<div class="form-group col-md-4">							
			<form:label path="convocatoria">
				<spring:message code="acta.convocatoria" />
			</form:label>	
			<form:select id="convocatoria" path="convocatoria" cssClass="form-control" >
				<spring:message code="master.page.select" var="optVacio" />
				<spring:message code="acta.diciembre" var="optDic" />
				<spring:message code="acta.febrero" var="optFeb" />
				<spring:message code="acta.junio" var="optJun" />
				<spring:message code="acta.septiembre" var="optSep" />
				<form:option value="" label="${optVacio}" />
				<form:option value="Diciembre" label="${optDic}" />
				<form:option value="Febrero" label="${optFeb}" />
				<form:option value="Junio" label="${optJun}" />
				<form:option value="Septiembre" label="${optSep}" />						
			</form:select>
			<br />	
			<form:errors cssClass="alert alert-danger medium" path="convocatoria" />			
		</div>	
		</div>
		
		
		<br />		
		<button name="save" type="submit" class="btn btn-primary" style="background-color: #3E58AA;border: 1px solid #39529e;color:white;"><spring:message code="acta.submit" /></button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-primary" style="background-color: #3E58AA;border: 1px solid #39529e;color:white;" onclick="javascript: window.location.replace('oferta/display.do?ofertaId=${actaForm.idOferta}');"  >
			<spring:message code="actor.cancel" />
		</button>				
		
	</form:form>

</fieldset>