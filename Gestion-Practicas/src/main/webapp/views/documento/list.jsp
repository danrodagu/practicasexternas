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
<form:form id="form" action="uploadServlet"  enctype="multipart/form-data">
	<div class="input-group mb-3">                     
		<div class="custom-file">	
	   		<input id="inputFile" class="form-control" type="text" onclick="document.getElementById('file').click();"/>
	       	<div class="input-group-append">
	    		<span class="input-group-text" onclick="document.getElementById('file').click();" style="cursor: pointer;"><spring:message code="documento.browse" /></span>
	  			<span id="uploadBtn" class="input-group-text" style="cursor: pointer;"><spring:message code="documento.upload" /></span>
	  		</div>
	 	</div>	 	
	    <input id="file" type="file" name="file" class="btn btn-default" onchange="mostrarArchivo()" style="visibility:hidden;"/>           
	</div> 
	<input id="titulo" type="text" name="titulo" style="visibility:hidden;"/>
	<input id="ofertaId" type="text" name="ofertaId" style="visibility:hidden;"/>
	<!-- <input id="alumno" type="text" name="alumno" style="visibility:hidden;"/> -->
</form:form>
<br />
<div class="table-responsive">
	<display:table name="documentos" id="row" requestURI="documento/list.do"
		pagesize="10" class="table table-hover">	
	
		<spring:message code="documento.titulo" var="tituloHeader" />
		<display:column property="titulo" title="${tituloHeader}" />
		
		<spring:message code="documento.uploader" var="uploaderHeader" />
		<display:column property="uploader.userAccount.username" title="${uploaderHeader}" />
		
		<display:column>
			<gp:iconUrl url="/Gestion-Practicas/downloadServlet?id=${row.id}" icon="fas fa-file-download" name="documento.download" color="Crimson"/>
		</display:column>		
		
		<display:column class="remove"> 
			<gp:iconUrl url="documento/delete.do?documentoId=${row.id}" deleteConfirmMsg="documento.delete.confirm" icon="fas fa-trash-alt" name="documento.delete" color="Crimson"/>
		</display:column>
		
	</display:table>
	
	<div align="right">
		<jstl:if test="${oferta.enEvaluacion && not oferta.docuCerrada}">
			<security:authorize access="hasRole('ADMINISTRATIVO') || hasRole('COORDINADOR')">
				<a href="oferta/cerrarDocumentacion.do?ofertaId=${oferta.id}" class="btn btn-danger" role="button"><spring:message code="documento.cerrar" /></a>
			</security:authorize>
		</jstl:if>
		
		<jstl:if test="${oferta.enEvaluacion && oferta.docuCerrada}">
			<security:authorize access="hasRole('ADMINISTRATIVO') || hasRole('COORDINADOR')">
				<a href="oferta/abrirDocumentacion.do?ofertaId=${oferta.id}" class="btn btn-danger" role="button"><spring:message code="documento.abrir" /></a>
			</security:authorize>
		</jstl:if>
	</div>
</div>

<script type="text/javascript">
	function mostrarArchivo(){
		var fi = document.getElementById('file'); 
		
		if(fi.files.length > 0){
			var nameFile = fi.files.item(0).name;
			var sizeFile = fi.files.item(0).size;
			/* fi.files.type */
			
			$('#inputFile').val(nameFile + " (" + Math.round((sizeFile / 1024)) + " KB)");
		}else{
			$('#inputFile').val('');
		}
	}
	
	$('#uploadBtn').click(function(){
		var file = document.getElementById('file'); 
		
		$('#titulo').val(file.files.item(0).name);
		$('#ofertaId').val('${oferta.id}');
		/* $('#alumno').val('${alumnoId}'); */
		$('#form').submit();
	});	
	
	$(document).ready(function() {
		if('${oferta.enEvaluacion}' == 'true' && '${esAlumno}' == 'true'){
			$('#form').remove();
			$(".remove").remove();
		}
		if('${oferta.docuCerrada}' == 'true'){
			$('#form').remove();
			$(".remove").remove();
		}
	});
	
</script>
