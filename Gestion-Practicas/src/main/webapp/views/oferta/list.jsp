<%--
 * list.jsp

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
<div class="table-responsive">
	<display:table name="ofertas" id="row" requestURI="oferta/list.do"
		pagesize="10" class="table table-hover">	
	
		<spring:message code="oferta.empresa" var="empresaHeader" />
		<display:column property="empresa" title="${empresaHeader}" />
		
		<spring:message code="oferta.duracion" var="duracionHeader" />
		<display:column property="duracion" title="${duracionHeader}" />
		
		<spring:message code="oferta.esCurricular" var="esCurricularHeader" />
		<display:column property="esCurricularStr" title="${esCurricularHeader}"/>
		
		<spring:message code="oferta.expedienteCerrado" var="expedienteCerradoHeader" />
		<display:column property="expedienteCerradoStr" title="${expedienteCerradoHeader}"/>
		
		<display:column>
			<gp:iconUrl url="oferta/display.do?ofertaId=${row.id}" icon="fas fa-eye" name="mensaje.display" color="Crimson"/>
		</display:column>
		
		<security:authorize access="hasRole('COORDINADOR') || hasRole('ADMINISTRATIVO')">
			<display:column>
				<gp:iconUrl url="oferta/edit.do?ofertaId=${row.id}" icon="fas fa-pencil-alt" name="oferta.edit" color="Crimson"/>
			</display:column>
		</security:authorize>
		
		<display:column>
			<gp:iconUrl url="documento/list.do?ofertaId=${row.id}" icon="fas fa-folder-open" name="alumno.documentos" color="Crimson"/>
		</display:column>
	</display:table>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		if(localStorage.getItem("language") == "en"){
			$('tr').find('td').contents().each(function(){				
				if(this.textContent == 'Cerrado'){
					this.textContent = 'Closed';
				}
				if(this.textContent == 'Abierto'){
					this.textContent = 'Open';
				}
			});
		}
		/* if(localStorage.getItem("language") == "es"){
			$('tr').find('td').contents().each(function(){				
				if(this.textContent == 'true'){
					this.textContent = 'Curricular';
				}
				if(this.textContent == 'false'){
					this.textContent = 'Extracurricular';
				}
			});
		} */	
		
	});
</script>
