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
	<div align="right">
		<a href="mensaje/create.do" class="btn btn-danger" role="button"><spring:message code="mensaje.create" /></a>
	</div>
	<br />
	<display:table name="carpetas" id="row" requestURI="carpeta/list.do"
		pagesize="5" class="table table-hover">
	
		<spring:message code="carpeta.nombre" var="nameHeader" />
		<display:column property="nombre" title="${nameHeader}" />
		
		<display:column>
			<gp:iconUrl url="mensaje/list.do?carpetaId=${row.id}" icon="fas fa-envelope" name="carpeta.mensajes" color="Crimson"/>
		</display:column>
	
	</display:table>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		if(localStorage.getItem("language") == "en"){
			$('tr').find('td').contents().each(function(){				
				if(this.textContent == 'Recibido'){
					this.textContent = 'Inbox';
				}
				if(this.textContent == 'Enviado'){
					this.textContent = 'Outbox';
				}
				if(this.textContent == 'Papelera'){
					this.textContent = 'Trashbox';
				}
			});
		}		
	});
</script>
