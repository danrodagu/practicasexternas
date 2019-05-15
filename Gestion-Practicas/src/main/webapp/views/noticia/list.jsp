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

<br />
<div class="table-responsive" style="float:left;width:70%;padding-right: 5%">
<div align="right">
	<security:authorize access="hasRole('COORDINADOR') || hasRole('ADMINISTRATIVO')">
		<%-- <gp:buttonUrl url="noticia/create.do" code="noticia.create"/> --%>
		<%-- <gp:iconUrl url="noticia/create.do" icon="fas fa-plus-circle" name="noticia.create" color="Crimson"/> --%>
		<a href="noticia/create.do" class="btn btn-primary" style="background-color: #3E58AA;border: 1px solid #39529e;color:white;" role="button"><spring:message code="noticia.create" /></a>
	</security:authorize>
</div>
<br />
<!-- <div style="float:left;"> -->
<display:table name="noticias" id="row" requestURI="noticia/list.do"
	pagesize="10" class="table table-hover" >
	
	<spring:message code="noticias" var="noticiasHeader" />
	<spring:message code="noticia.fechaModificacion" var="fechaModificacionHeader" />
	<display:column title="${noticiasHeader}">
		<h4>${row.titulo}</h4>
		<br />		
		<span style="font-size: 15px; color: #3E58AA;">
			<i class="fas fa-calendar-alt"></i>
		</span>
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
<!-- </div> -->

<!-- <div style="float:right;position:absolute;display:inline;">
			<a class="twitter-timeline" data-lang="es" data-width="300" data-height="800" data-theme="light" data-link-color="#2B7BB9" href="https://twitter.com/Etsii_practicas?ref_src=twsrc%5Etfw">Tweets by Etsii_practicas</a> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>
		</div> -->
</div>
<div style="float:right;width:30%;display:inline-block;padding-top: 6%"> <!-- display:inline-block; -->
		<a class="twitter-timeline" data-lang="es" data-width="300" data-height="800" data-theme="light" data-link-color="#2B7BB9" href="https://twitter.com/Etsii_practicas?ref_src=twsrc%5Etfw">Tweets by Etsii_practicas</a> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>
	</div>	



