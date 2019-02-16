<%--
 * index.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="welcome.greeting.prefix" /> ${name}<spring:message code="welcome.greeting.suffix" /></p>

<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p>

<form:form id="form" action="sendEmailServlet" >
<span id="uploadBtn" class="input-group-text" style="cursor: pointer;"><spring:message code="documento.upload" /></span>
</form:form>



<script type="text/javascript">	
	
	$('#uploadBtn').click(function(){		
		$('#form').submit();
	});	
	
</script>