<%--
 * index.jsp

 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<br />
<br />

<%-- <div>	
	<div style="float:left;width:70%;">
		<spring:message code="welcome.greeting.text" />
	</div>
	
	<div style="float:right;width:30%;"> <!-- display:inline-block; -->
		<a class="twitter-timeline" data-lang="es" data-width="300" data-height="800" data-theme="light" data-link-color="#2B7BB9" href="https://twitter.com/Etsii_practicas?ref_src=twsrc%5Etfw">Tweets by Etsii_practicas</a> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>
	</div>	
</div> --%>

<div align="center">	
	<img src="images/logoUS.gif" alt="US" class="img-fluid" style="width:30%" />	
	<br />
	<br />
	<br />
	<spring:message code="welcome.greeting.text" />	
</div>