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
<nav aria-label="breadcrumb" >
  <ol class="breadcrumb">
    <li class="breadcrumb-item"><a href="documento/list.do?ofertaId=${oferta.id}"><spring:message code="alumno.documentos" /></a></li>
    <security:authorize access="hasRole('TUTOR') || hasRole('COORDINADOR')">
	    <jstl:if test="${oferta.docuCerrada && not oferta.evaluada}">
	    	<li class="breadcrumb-item"><a href="valoracion/create.do?ofertaId=${oferta.id}"><spring:message code="oferta.evaluar" /></a></li>
	  	</jstl:if>
  	</security:authorize>
  	<security:authorize access="hasRole('ADMINISTRATIVO')">
  		<jstl:if test="${oferta.evaluada && not oferta.preacta}">
	    	<li class="breadcrumb-item"><a href="documento/acta/create.do?ofertaId=${oferta.id}"><spring:message code="oferta.generarActa" /></a></li>
	  	</jstl:if>
  	</security:authorize>
  	<security:authorize access="hasRole('ADMINISTRATIVO')">
  		<jstl:if test="${oferta.evaluada && oferta.actaFirmada && not oferta.expedienteCerrado}">
  			<spring:message code="expediente.administrativo.confirm" var="confirmHeader" />
	    	<li class="breadcrumb-item"><a href="oferta/cerrarExpediente.do?ofertaId=${oferta.id}" onclick="return confirm('${confirmHeader}')"><spring:message code="oferta.cerrarExp" /></a></li>
	  	</jstl:if>
  	</security:authorize> 
  	<security:authorize access="hasRole('COORDINADOR') || hasRole('ADMINISTRATIVO')">
  		<jstl:if test="${oferta.enEvaluacion}">
	    	<li class="breadcrumb-item"><a href="oferta/invalidaEvaluacion.do?ofertaId=${oferta.id}"><spring:message code="oferta.invalidarEvaluacion" /></a></li>
	  	</jstl:if>
  	</security:authorize>   	
  </ol>
</nav>

<br />
<spring:message code="oferta.alumno" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.alumnoAsignado.nombreCompleto}" />
&nbsp;
<security:authorize access="!hasRole('ALUMNO')">
	<gp:iconUrl url="mensaje/create.do?actorId=${oferta.alumnoAsignado.id}" icon="fas fa-envelope" name="oferta.contactar" color="Crimson"/>
</security:authorize>
<br />

<spring:message code="oferta.tutor" />:
&nbsp;&nbsp;
<jstl:out value="${oferta.tutorAsignado.nombreCompleto}" />
&nbsp;
<security:authorize access="!hasRole('TUTOR')">
	<gp:iconUrl url="mensaje/create.do?actorId=${oferta.tutorAsignado.id}" icon="fas fa-envelope" name="oferta.contactar" color="Crimson"/>
</security:authorize>
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
<br />



