<%--
 * display.jsp

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


<br />
<spring:message code="oferta.alumno" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.alumnoAsignado.nombreCompleto}" />
<br />

<spring:message code="oferta.tutor" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.tutorAsignado.nombreCompleto}" />
<br />

<div class="dropdown-divider"></div>

<spring:message code="oferta.titulo" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.titulo}" />
<br />

<spring:message code="oferta.descripcion" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.descripcion}" />
<br />

<spring:message code="oferta.duracion" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.duracion}" />
<br />

<spring:message code="oferta.fechaInicio" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.fechaInicio}" />
<br />

<spring:message code="oferta.fechaFin" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.fechaFin}" />
<br />

<spring:message code="oferta.horas" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.horas}" />
<br />

<spring:message code="oferta.dotacion" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.dotacion}" />
<br />

<spring:message code="oferta.esCurricular" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.esCurricularStr}" />
<br />

<spring:message code="oferta.pais" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.pais}" />
<br />

<spring:message code="oferta.provincia" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.provincia}" />
<br />

<spring:message code="oferta.localidad" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.localidad}" />
<br />

<div class="dropdown-divider"></div>
<h4><spring:message code="oferta.datos.empresa" /></h4>
<br />

<spring:message code="oferta.empresa" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.empresa}" />
<br />

<spring:message code="oferta.cifEmp" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.cifEmp}" />
<br />

<spring:message code="oferta.telefonoEmp" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.telefonoEmp}" />
<br />

<spring:message code="oferta.emailEmp" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.emailEmp}" />
<br />

<spring:message code="oferta.tutorEmp" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.tutorEmp}" />
<br />
