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
<display:table name="noticias" id="row" requestURI="noticia/list.do"
	pagesize="10" class="table table-hover">
	
	<spring:message code="noticia.fechaModificacion" var="fechaModificacionHeader" />
	<display:column title="${fechaModificacionHeader}" sortable="true">
		<jstl:out value="${row.fechaModificacionHeader}" ></jstl:out>
	 </display:column>

	<spring:message code="noticia.titulo" var="tituloHeader" />
	<display:column property="titulo" title="${tituloHeader}" />
		
	<display:column>
		<gp:iconUrl url="noticia/display.do?noticiaId=${row.id}" icon="fas fa-eye" name="noticia.display" color="Crimson"/>
	</display:column>

	<display:column>
		<gp:iconUrl url="noticia/edit.do?noticiaId=${row.id}" icon="fas fa-eye" name="noticia.edit" color="Crimson"/>
	</display:column>
	
	<display:column>
		<gp:iconUrl url="noticia/delete.do?noticiaId=${row.id}" deleteConfirmMsg="noticia.delete.confirm" icon="fas fa-trash-alt" name="noticia.delete" color="Crimson"/>
	</display:column>
	

</display:table>

<gp:buttonUrl url="noticia/create.do" code="noticia.create"/>
</div>


