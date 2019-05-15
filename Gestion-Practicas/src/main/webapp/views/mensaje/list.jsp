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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="gp" tagdir="/WEB-INF/tags"%>

<div align="right">
	<a onclick="window.location.replace('carpeta/list.do');" class="btn btn-primary" style="background-color: #3E58AA;border: 1px solid #39529e;color:white;" role="button"><spring:message code="master.page.atras" /></a>
</div>
<br />

<div class="table-responsive">
	<display:table name="mensajes" id="row" requestURI="${requestURI}"
		pagesize="10" class="table table-hover">
		
		<spring:message code="mensaje.fecha" var="fechaHeader" />
		<display:column title="${fechaHeader}" sortable="true">
			<fmt:formatDate value="${row.fecha}" pattern="dd/MM/yyyy HH:mm" />
		</display:column>
	
		<spring:message code="mensaje.asunto" var="asuntoHeader" />
		<display:column property="asunto" title="${asuntoHeader}" sortable="true"/>
	
		<jstl:if test="${carpeta.nombre eq 'Recibido' || carpeta.nombre eq 'Papelera'}">
			<spring:message code="mensaje.emisor" var="emisorHeader" />
			<display:column property="emisor.nombreCompleto" title="${emisorHeader}" sortable="true"/>
		</jstl:if>
	
		<jstl:if test="${carpeta.nombre eq 'Enviado' || carpeta.nombre eq 'Papelera'}">
			<spring:message code="mensaje.receptor" var="receptorHeader" />
			<display:column property="receptor.nombreCompleto" title="${receptorHeader}" sortable="true" />
		</jstl:if>		
	
		<spring:message code="mensaje.leido" var="leidoHeader" />
		<display:column property="leidoStr" title="${leidoHeader}" sortable="true"/>
	
			
		<display:column>
			<gp:iconUrl url="mensaje/display.do?mensajeId=${row.id}" icon="fas fa-eye" name="mensaje.display" color="Crimson"/>
		</display:column>
		
		
		<display:column>
			<gp:iconUrl url="mensaje/delete.do?mensajeId=${row.id}" deleteConfirmMsg="mensaje.delete.confirm" icon="fas fa-trash-alt" name="mensaje.delete" color="Crimson"/>
		</display:column>
		
	
	</display:table>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		if(localStorage.getItem("language") == "en"){
			$('tr').find('td').contents().each(function(){				
				if(this.textContent == 'Sí'){
					this.textContent = 'Yes';
				}
			});
		}		
	});
</script>

