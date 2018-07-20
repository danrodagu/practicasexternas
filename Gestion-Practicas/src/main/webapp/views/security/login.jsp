 <%--
 * login.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<br />

<form:form action="j_spring_security_check" modelAttribute="credentials">
	
	<div class="input-group mb-2">	  
		<div class="input-group-prepend">
			<span class="input-group-text" id="basic-addon1">@</span>
		</div>
		<form:input id="username" path="username" cssClass="form-control col-sm-3" aria-describedby="basic-addon1" required="required" />	
		<form:errors class="error" path="username" />	
	</div>
	
	<br />

	<div class="input-group mb-2">
	  <div class="input-group-prepend">
	  	<span class="input-group-text" id="basic-addon2">
			<img src="images/password.png" style="height:15px;width:auto" />
		</span>
	  </div>
		<form:password id="password" path="password" cssClass="form-control col-sm-3" aria-describedby="basic-addon2" required="required"/>	
		<form:errors class="error" path="password" />
	</div>
	
	
	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="security.login.failed" />
		</div>
	</jstl:if>
	<br />
	
	<input type="submit" value="<spring:message code="security.login" />" />	
		
</form:form>

<script type="text/javascript">
	document.getElementById("username").placeholder='<spring:message code="security.username" />';
	document.getElementById("password").placeholder='<spring:message code="security.password" />';	
</script>	