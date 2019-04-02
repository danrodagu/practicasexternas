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


<div class="table-responsive">
<display:table name="mensajes" id="row" requestURI="${requestURI}"
	pagesize="10" class="table table-hover">
	
	<spring:message code="mensaje.fecha" var="fechaHeader" />
	<display:column property="fecha" title="${fechaHeader}" sortable="true" />

	<spring:message code="mensaje.asunto" var="asuntoHeader" />
	<display:column property="asunto" title="${asuntoHeader}" />

	<spring:message code="mensaje.emisor" var="emisorHeader" />
	<display:column property="emisor.userAccount.username" title="${emisorHeader}" />

	<spring:message code="mensaje.receptor" var="receptorHeader" />
	<display:column property="receptor.userAccount.username" title="${receptorHeader}" />

	<spring:message code="mensaje.leido" var="leidoHeader" />
	<display:column property="leidoStr" title="${leidoHeader}" />

		
	<display:column>
		<gp:iconUrl url="mensaje/display.do?mensajeId=${row.id}" icon="fas fa-eye" name="mensaje.display" color="Crimson"/>
	</display:column>
	
	
	<display:column>
		<gp:iconUrl url="mensaje/reply.do?mensajeId=${row.id}&actorId=${row.emisor.id}" icon="fas fa-reply" name="mensaje.reply" color="Crimson"/>
	</display:column>

	
	<display:column>
		<gp:iconUrl url="mensaje/forward.do?mensajeId=${row.id}" icon="fas fa-reply-all" name="mensaje.forward" color="Crimson"/>
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

