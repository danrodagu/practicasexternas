<%--
 * edit.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="gp" tagdir="/WEB-INF/tags"%>

<br />	
<fieldset>
	<form:form action="valoracion/edit.do" modelAttribute="valoracionForm">
		<form:hidden path="id" />
		<form:hidden path="idOferta" />


		<div class="row">
			<div class="form-group col-md-8">
				<gp:textarea id="texto" cols="20" rows="10" code="valoracion.texto" path="texto" cssClass="form-control" required="required" />
			</div>
		</div>
		
		<spring:message code="master.page.select" var="optVacio" />
		<div class="row">
			<div class="form-group col-md-4">						
				<div>
					<jstl:if test="${oferta.esCurricular}">					
						<gp:textbox cssClass="form-control" code="valoracion.calificacion" path="notaCurricular" id="notaCurricular" required="required"/>						
					</jstl:if>
					<jstl:if test="${not oferta.esCurricular}">					
						<form:label path="notaExtracurricular">
							<spring:message code="valoracion.calificacion" />
						</form:label>	
						<form:select id="extracurriculares" path="notaExtracurricular" cssClass="form-control">
							<form:option value="" label="${optVacio}" />
							<form:option id="apto" value="APTO" label="APTO" />
							<form:option id="noApto" value="NO APTO" label="NO APTO" />						
						</form:select>
						<br />	
						<form:errors cssClass="alert alert-danger medium" path="notaExtracurricular" />
					</jstl:if>	
				</div>
			</div>
		</div>		
		
		<br />		
		<button name="save" type="submit" class="btn btn-primary" style="background-color: #3E58AA;border: 1px solid #39529e;color:white;"><spring:message code="valoracion.submit" /></button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-primary" style="background-color: #3E58AA;border: 1px solid #39529e;color:white;" onclick="javascript: window.location.replace('oferta/display.do?ofertaId=${oferta.id}');"  >
			<spring:message code="actor.cancel" />
		</button>				
		
	</form:form>

</fieldset>

<script type="text/javascript">
	$(document).ready(function() {
		if(localStorage.getItem("language") == "en"){
			$('select').find('option').contents().each(function(){				
				if(this.textContent == 'APTO'){
					this.textContent = 'PASS';
				}
				if(this.textContent == 'NO APTO'){
					this.textContent = 'FAIL';
				}
			});
		}		
	});
</script>
