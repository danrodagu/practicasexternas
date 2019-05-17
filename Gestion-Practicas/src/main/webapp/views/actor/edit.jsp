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
	<form:form action="actor/edit.do" modelAttribute="edicionPerfilForm">
		<form:hidden path="id" />

		
		<div class="row">
			<jstl:if test="${rolLogueado == 'ADMINISTRATIVO'}">
				<div class="form-group col-md-4">
					<gp:textbox id="nif" code="actor.nif" path="nif" cssClass="form-control" required="required" />
				</div>
				
				<div class="form-group col-md-4">
					<gp:textbox id="nombre" code="actor.nombre" path="nombre" cssClass="form-control" required="required" />
				</div>
				<div class="form-group col-md-4">
					<gp:textbox id="apellidos" code="actor.apellidos" path="apellidos" cssClass="form-control" required="required"/>				
				</div>
			</jstl:if>
			<jstl:if test="${rolLogueado != 'ADMINISTRATIVO'}">
				<div class="form-group col-md-4">
					<gp:textbox id="nif" code="actor.nif" path="nif" cssClass="form-control" required="required" readonly="true" />
				</div>
				
				<div class="form-group col-md-4">
					<gp:textbox id="nombre" code="actor.nombre" path="nombre" cssClass="form-control" required="required" readonly="true"/>
				</div>
				<div class="form-group col-md-4">
					<gp:textbox id="apellidos" code="actor.apellidos" path="apellidos" cssClass="form-control" required="required" readonly="true"/>				
				</div>
			</jstl:if>
		</div>
		
		
		<div class="row">
			<jstl:if test="${rolLogueado == 'ADMINISTRATIVO'}">
				<div class="form-group col-md-4">
					<gp:textbox id="username" code="actor.username" path="username" cssClass="form-control" required="required" />
				</div>							
				<jstl:if test="${rolPerfil == 'ALUMNO'}">
					<div class="form-group col-md-4">
						<gp:textbox id="titulacion" code="actor.titulacion" path="titulacion" cssClass="form-control" required="required" />
					</div>
				</jstl:if>			
				<jstl:if test="${rolPerfil == 'TUTOR' || rolPerfil == 'COORDINADOR'}">
					<div class="form-group col-md-4">
						<gp:textbox id="departamento" code="actor.departamento" path="departamento" cssClass="form-control" required="required" />
					</div>
				</jstl:if>
			</jstl:if>
			
			<jstl:if test="${rolLogueado != 'ADMINISTRATIVO'}">
				<div class="form-group col-md-4">
					<gp:textbox id="username" code="actor.username" path="username" cssClass="form-control" required="required" readonly="true"/>
				</div>		
				<jstl:if test="${rolPerfil == 'ALUMNO'}">
					<div class="form-group col-md-4">
						<gp:textbox id="titulacion" code="actor.titulacion" path="titulacion" cssClass="form-control" required="required" readonly="true"/>
					</div>
				</jstl:if>
				
				<jstl:if test="${rolPerfil == 'TUTOR' || rolPerfil == 'COORDINADOR'}">
					<div class="form-group col-md-4">
						<gp:textbox id="departamento" code="actor.departamento" path="departamento" cssClass="form-control" required="required" readonly="true"/>
					</div>
				</jstl:if>					
			</jstl:if>
			
			<div class="form-group col-md-4">
				<gp:textbox id="email" code="actor.email" path="email" cssClass="form-control" required="required" />
			</div>
		</div>
			
		<jstl:if test="${mismoActorLogYPerfil}">
			<div class="dropdown-divider"></div>
			<div class="row">
				<div class="form-group col-md-4">
					<gp:password id="password" code="actor.change.password" path="password" cssClass="form-control" />
				</div>
				<div class="form-group col-md-4">
					<gp:password id="password2" code="actor.password2" path="password2" cssClass="form-control" />				
				</div>
			</div>
		</jstl:if>
		
		<br />
		<button name="save" type="submit" class="btn btn-primary" style="background-color: #3E58AA;border: 1px solid #39529e;color:white;"><spring:message code="crear.submit" /></button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-primary" style="background-color: #3E58AA;border: 1px solid #39529e;color:white;" onclick="javascript: window.history.back();"  >
			<spring:message code="actor.cancel" />
		</button>				
		
	</form:form>

</fieldset>

<script type="text/javascript">
	$(document).ready(function() {
		$( '#password' ).removeAttr('oninvalid');
		$( '#password' ).removeAttr('oninput');
		$( '#password2' ).removeAttr('oninvalid');
		$( '#password2' ).removeAttr('oninput');
	});	
	
	$('#password').change(function() {
	    if($(this).val() != ''){
	        $(this).prop('required', true);
	        $( '#password2' ).prop('required', true);
	    }else{
	        if($('#password2').val() == ''){
	            $(this).prop('required', false);
	            $( '#password2' ).prop('required', false);
	        }           
	    }       
	});
	
	$('#password2').change(function() {
	    if($(this).val() != ''){
	        $(this).prop('required', true);
	        $('#password').prop('required', true);
	    }else{
	        if($('#password').val() == ''){
	            $(this).prop('required', false);
	            $('#password').prop('required', false);
	        }           
	    }
	});
	/* $('#password').change(function() {
		if($(this).val() != ''){
			$(this).attr('required', true);			
			$( '#password2' ).attr('required', true);
			
			if(localStorage.getItem("language") == "en"){
				$(this).attr("oninvalid", "this.setCustomValidity('Fill out this field')");
				$(this).attr("oninput", "this.setCustomValidity('')");
				
				$( '#password2' ).attr("oninvalid", "this.setCustomValidity('Fill out this field')");
				$( '#password2' ).attr("oninput", "this.setCustomValidity('')");
			}
			if(localStorage.getItem("language") == "es"){
				$(this).attr("oninvalid", "this.setCustomValidity('Completa este campo')");
				$(this).attr("oninput", "this.setCustomValidity('')");
				
				$( '#password2' ).attr("oninvalid", "this.setCustomValidity('Completa este campo')");
				$( '#password2' ).attr("oninput", "this.setCustomValidity('')");
			}
		}else{
			if($('#password2').val() == ''){
				$(this).removeAttr('required');
				$(this).removeAttr('oninvalid');
				$(this).removeAttr('oninput');
				
				$( '#password2' ).removeAttr('required');
				$( '#password2' ).removeAttr('oninvalid');
				$( '#password2' ).removeAttr('oninput');
			}			
		}
	}); */
	
	/* $('#password2').change(function() {
		if($(this).val() != ''){
			$(this).attr('required', true);
			$('#password').attr('required', true);
		}else{
			if($('#password').val() == ''){
				$(this).removeAttr('required');
				$('#password').removeAttr('required');
			}			
		}
	}); */	
</script>
