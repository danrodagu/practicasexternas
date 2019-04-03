<%--
 * list.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="gp" tagdir="/WEB-INF/tags"%>


<div class="table-responsive">
<security:authorize access="hasRole('COORDINADOR') || hasRole('ADMINISTRATIVO')">
	<%-- <gp:buttonUrl url="noticia/create.do" code="noticia.create"/> --%>
	<gp:iconUrl url="noticia/create.do" icon="fas fa-plus-circle" name="noticia.create" color="Crimson"/>
</security:authorize>
<display:table name="noticias" id="row" requestURI="noticia/list.do"
	pagesize="10" class="table table-hover">
	
	<spring:message code="noticias" var="noticiasHeader" />
	<spring:message code="noticia.fechaModificacion" var="fechaModificacionHeader" />
	<display:column title="${noticiasHeader}">
		<h4>${row.titulo}</h4>
		<br />
		<i style="font-size:medium;"> ${fechaModificacionHeader}&nbsp;<fmt:formatDate value="${row.fechaModificacion}" pattern="dd/MM/yyyy HH:mm" />	</i>	
		<br /><br />
		${row.cuerpo}
		
	 </display:column>
		
	<%-- <display:column>
		<gp:iconUrl url="noticia/display.do?noticiaId=${row.id}" icon="fas fa-eye" name="noticia.display" color="Crimson"/>
	</display:column> --%>

	<security:authorize access="hasRole('COORDINADOR') || hasRole('ADMINISTRATIVO')">
		<display:column>
			<gp:iconUrl url="noticia/edit.do?noticiaId=${row.id}" icon="fas fa-pencil-alt" name="noticia.edit" color="Crimson"/>
		</display:column>
		
		<display:column>
			<gp:iconUrl url="noticia/delete.do?noticiaId=${row.id}" deleteConfirmMsg="noticia.delete.confirm" icon="fas fa-trash-alt" name="noticia.delete" color="Crimson"/>
		</display:column>
	</security:authorize>
		
</display:table>

</div>


