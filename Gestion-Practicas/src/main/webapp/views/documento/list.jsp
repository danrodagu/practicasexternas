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

<%-- <div class="input-group mb-3">
	  <div class="custom-file">	    
	    <input type="file" class="custom-file-input" id="inputGroupFile02">
	    <label class="custom-file-label" for="inputGroupFile02" aria-describedby="inputGroupFileAddon02"><spring:message code="documento.chooseFile" /></label>
	  </div>
	  <div class="input-group-append">
	    <span class="input-group-text" id="inputGroupFileAddon02"><spring:message code="documento.upload" /></span>
	  </div>
	</div> --%>
	
<div class="input-group mb-3">                     
	<div class="custom-file">	
   		<input id="inputFile" class="form-control" type="text" onclick="document.getElementById('file').click();"/>
       	<div class="input-group-append">
    		<span class="input-group-text" onclick="document.getElementById('file').click();" style="cursor: pointer;"><spring:message code="documento.browse" /></span>
  			<span class="input-group-text" onclick="document.getElementById('file').click();" style="cursor: pointer;"><spring:message code="documento.upload" /></span>
  		</div>
 	</div>
    <input id="file" type="file" class="btn btn-default" onchange="mostrarArchivo()" style="visibility:hidden;"/>           
</div> 

<display:table name="documentos" id="row" requestURI="documento/list.do"
	pagesize="10" class="table table-hover">	

	<spring:message code="documento.titulo" var="tituloHeader" />
	<display:column property="titulo" title="${nameHeader}" />
	
	<spring:message code="documento.uploader" var="uploaderHeader" />
	<display:column property="uploader" title="${uploaderHeader}" />

</display:table>

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
</script>
