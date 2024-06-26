<%--
 * create.jsp

 --%>


<%@page language="java" contentType="cuerpo/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="gp" tagdir="/WEB-INF/tags"%>

<form:form id="formId" action="noticia/edit.do" modelAttribute="noticiaForm">
	<form:hidden path="id" />
	<fieldset>
		
		<div class="form-group col-md-4">
			<gp:textbox id="titulo" code="noticia.titulo" path="titulo" cssClass="form-control" />			
		</div>
				
		<%-- Editor de noticias QuillJS --%>
		
		<div class="form-group col-md-10">
			<form:label path="cuerpo">
				<spring:message code="noticia.cuerpo" />
			</form:label>
			<div id="toolbar">
			  <span class=ql-formats>
				  <select class="ql-font">
				    <option selected="selected"></option>
				    <option value="serif"></option>
				    <option value="monospace"></option>
				  </select>
				  <select class="ql-size">
				    <option value="small"><spring:message code="quill.small" /></option>
				    <option selected></option>
				    <option value="large"><spring:message code="quill.large" /></option>
				    <option value="huge"><spring:message code="quill.huge" /></option>
				  </select>
			  </span>		
			  <span class=ql-formats>
				  <button class="ql-bold"></button>
				  <button class="ql-italic"></button>
				  <button class="ql-underline"></button>
				  <button class="ql-strike"></button>  
			  </span>	
			  <span class=ql-formats>
			  	<select class="ql-color"><option selected="selected"></option><option value="#e60000"></option><option value="#ff9900"></option><option value="#ffff00"></option><option value="#008a00"></option><option value="#0066cc"></option><option value="#9933ff"></option><option value="#ffffff"></option><option value="#facccc"></option><option value="#ffebcc"></option><option value="#ffffcc"></option><option value="#cce8cc"></option><option value="#cce0f5"></option><option value="#ebd6ff"></option><option value="#bbbbbb"></option><option value="#f06666"></option><option value="#ffc266"></option><option value="#ffff66"></option><option value="#66b966"></option><option value="#66a3e0"></option><option value="#c285ff"></option><option value="#888888"></option><option value="#a10000"></option><option value="#b26b00"></option><option value="#b2b200"></option><option value="#006100"></option><option value="#0047b2"></option><option value="#6b24b2"></option><option value="#444444"></option><option value="#5c0000"></option><option value="#663d00"></option><option value="#666600"></option><option value="#003700"></option><option value="#002966"></option><option value="#3d1466"></option></select>
			  	<select class="ql-background"><option selected="selected"></option><option value="#e60000"></option><option value="#ff9900"></option><option value="#ffff00"></option><option value="#008a00"></option><option value="#0066cc"></option><option value="#9933ff"></option><option value="#ffffff"></option><option value="#facccc"></option><option value="#ffebcc"></option><option value="#ffffcc"></option><option value="#cce8cc"></option><option value="#cce0f5"></option><option value="#ebd6ff"></option><option value="#bbbbbb"></option><option value="#f06666"></option><option value="#ffc266"></option><option value="#ffff66"></option><option value="#66b966"></option><option value="#66a3e0"></option><option value="#c285ff"></option><option value="#888888"></option><option value="#a10000"></option><option value="#b26b00"></option><option value="#b2b200"></option><option value="#006100"></option><option value="#0047b2"></option><option value="#6b24b2"></option><option value="#444444"></option><option value="#5c0000"></option><option value="#663d00"></option><option value="#666600"></option><option value="#003700"></option><option value="#002966"></option><option value="#3d1466"></option></select>
			  </span>	  
			  <span class=ql-formats>
				  <button class="ql-align" value=""></button>
				  <button class="ql-align" value="center"></button>
				  <button class="ql-align" value="right"></button>
				  <button class="ql-align" value="justify"></button>
			  </span>	  
			  <span class=ql-formats>
				  <button class="ql-list" value="ordered"></button> 
				  <button class="ql-list" value="bullet"></button>  
			  </span>			  
			  <span class=ql-formats>  
				  <button class="ql-link"></button>
			  </span>
		    </div>		    
		    <input id="cuerpo" name="cuerpo" type="hidden">
		    <div id="editor">	    		
		    	<p>${noticiaForm.cuerpo}</p>		    		
		    </div>
		    
			<br />
			<jstl:if test="${not empty validaCuerpo}">
				<div class="alert alert-danger medium" role="alert">
					<spring:message code="${validaCuerpo}" />
				</div>
			</jstl:if>
			
			<br />
			
			<button id="save" name="save" type="submit" class="btn btn-primary" style="background-color: #3E58AA;border: 1px solid #39529e;color:white;"><spring:message code="noticia.submit" /></button>
			&nbsp;&nbsp;
			<button type="button" class="btn btn-primary" style="background-color: #3E58AA;border: 1px solid #39529e;color:white;" onclick="javascript: window.location.replace('noticia/list.do');">
				<spring:message code="actor.cancel" />
			</button>
		</div>		
		<br />

	</fieldset>	
</form:form>


<script type='text/javascript'>
	$(document).ready(function() {
			
		var quill = new Quill('#editor', {
		    modules : { 
		    	toolbar: '#toolbar',
		    },
			theme: 'snow'
	    });  
	    
	    
		
	    $("form").submit(function(e) {	    
	    	
	    	var myEditor = document.querySelector('#editor');
			var html = myEditor.children[0].innerHTML;			
			
			$.ajax({
	    			type : "POST",
	    			url : 'noticia/noticiaAjax.do',
	    			contentType: 'application/json; charset=utf-8',
	    		    data: JSON.stringify({'cuerpo': html}),
	    		    async: false,
	    		    cache: false,
	    			success: function(callback){	    				
	    				if(callback.error != null){
	    					e.preventDefault();
	    					alert('Error interno');
	    				}				
	    			},
	    			error: function (xhr) {
	    				e.preventDefault();
	    		        alert('Error ' + xhr.status);
	    		    }
	    		});
			
	    });
	    
	});
</script>